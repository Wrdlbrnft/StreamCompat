package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.exceptions.StreamException;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

class DoubleIteratorWrapperImpl implements DoubleIteratorWrapper {

    private interface Wrapper {
        <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer);
        <E extends Throwable> void mapException(Class<E> exceptionClass, ToDoubleFunction<E> mapper);
    }

    private Wrapper mWrapper;

    @Override
    public DoubleIterator apply(DoubleIterator iterator) {
        final WrapperImpl wrapper = new WrapperImpl(iterator);
        mWrapper = wrapper;
        return wrapper;
    }

    @Override
    public <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer) {
        mWrapper.consumeException(exceptionClass, consumer);
    }

    @Override
    public <E extends Throwable> void mapException(Class<E> exceptionClass, ToDoubleFunction<E> mapper) {
        mWrapper.mapException(exceptionClass, mapper);
    }

    private static abstract class ExceptionHandler<E extends Throwable> {

        interface Result {
            boolean doesHandle();
            boolean hasMappedValue();
            double getMappedValue();
        }

        private final Class<E> mExceptionClass;

        private ExceptionHandler(Class<E> exceptionClass) {
            mExceptionClass = exceptionClass;
        }

        @SuppressWarnings("unchecked")
        Result handle(Throwable exception) {
            if (!mExceptionClass.isAssignableFrom(exception.getClass())) {
                return new ResultImpl(false, false, 0);
            }

            return performHandle((E) exception);
        }

        protected abstract Result performHandle(E exception);
    }

    private static class ConsumeExceptionHandler<E extends Throwable> extends ExceptionHandler<E> {

        private final Consumer<E> mConsumer;

        private ConsumeExceptionHandler(Class<E> exceptionClass, Consumer<E> consumer) {
            super(exceptionClass);
            mConsumer = consumer;
        }

        @Override
        protected Result performHandle(E exception) {
            mConsumer.accept(exception);
            return new ResultImpl(true, false, 0);
        }
    }

    private static class MappingExceptionHandler<E extends Throwable> extends ExceptionHandler<E> {

        private final ToDoubleFunction<E> mMapper;

        private MappingExceptionHandler(Class<E> exceptionClass, ToDoubleFunction<E> mapper) {
            super(exceptionClass);
            mMapper = mapper;
        }

        @Override
        protected Result performHandle(E exception) {
            return new ResultImpl(true, true, mMapper.apply(exception));
        }
    }

    private static class WrapperImpl implements DoubleIterator, Wrapper {

        private final List<ExceptionHandler<?>> mExceptionHandlers = new ArrayList<>();
        private final DoubleIterator mIterator;

        private boolean mEvaluated = false;
        private boolean mHasNext = false;
        private double mNext;

        private WrapperImpl(DoubleIterator iterator) {
            mIterator = iterator;
        }

        @Override
        public boolean hasNext() {
            if (!doesHandleExceptions()) {
                return mIterator.hasNext();
            }
            evaluate();
            return mHasNext;
        }

        @Override
        public Double next() {
            return nextDouble();
        }

        boolean doesHandleExceptions() {
            return !mExceptionHandlers.isEmpty();
        }

        @Override
        public double nextDouble() {
            if (!doesHandleExceptions()) {
                return mIterator.nextDouble();
            }
            evaluate();
            mEvaluated = false;
            if (mHasNext) {
                return mNext;
            }
            throw new NoSuchElementException();
        }

        private void evaluate() {
            if (mEvaluated) {
                return;
            }
            mEvaluated = true;
            while (mIterator.hasNext()) {
                try {
                    mNext = mIterator.next();
                    mHasNext = true;
                    return;
                } catch (Exception e) {
                    for (ExceptionHandler<?> handler : mExceptionHandlers) {
                        final ExceptionHandler.Result result = handler.handle(e);
                        if (result.doesHandle()) {
                            if (result.hasMappedValue()) {
                                mNext = result.getMappedValue();
                                mHasNext = true;
                                return;
                            }
                            continue;
                        }
                        throw new StreamException("Failed to evaluate item.", e);
                    }
                }
            }
            mHasNext = false;
        }

        @Override
        public <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer) {
            mExceptionHandlers.add(new ConsumeExceptionHandler<>(exceptionClass, consumer));
        }

        @Override
        public <E extends Throwable> void mapException(Class<E> exceptionClass, ToDoubleFunction<E> mapper) {
            mExceptionHandlers.add(new MappingExceptionHandler<>(exceptionClass, mapper));
        }
    }

    private static class ResultImpl implements ExceptionHandler.Result {

        private final boolean mDoesHandle;
        private final boolean mHasMappedValue;
        private final double mMappedValue;

        private ResultImpl(boolean doesHandle, boolean hasMappedValue, double mappedValue) {
            mDoesHandle = doesHandle;
            mHasMappedValue = hasMappedValue;
            mMappedValue = mappedValue;
        }

        @Override
        public boolean doesHandle() {
            return mDoesHandle;
        }

        @Override
        public boolean hasMappedValue() {
            return mHasMappedValue;
        }

        @Override
        public double getMappedValue() {
            return mMappedValue;
        }
    }
}
