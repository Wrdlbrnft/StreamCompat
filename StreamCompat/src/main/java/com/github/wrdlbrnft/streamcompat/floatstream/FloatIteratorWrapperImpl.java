package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.exceptions.StreamException;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

class FloatIteratorWrapperImpl implements FloatIteratorWrapper {

    private interface Wrapper {
        <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer);
        <E extends Throwable> void mapException(Class<E> exceptionClass, ToFloatFunction<E> mapper);
    }

    private Wrapper mWrapper;

    @Override
    public FloatIterator apply(FloatIterator iterator) {
        final WrapperImpl wrapper = new WrapperImpl(iterator);
        mWrapper = wrapper;
        return wrapper;
    }

    @Override
    public <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer) {
        mWrapper.consumeException(exceptionClass, consumer);
    }

    @Override
    public <E extends Throwable> void mapException(Class<E> exceptionClass, ToFloatFunction<E> mapper) {
        mWrapper.mapException(exceptionClass, mapper);
    }

    private static abstract class ExceptionHandler<E extends Throwable> {

        interface Result {
            boolean doesHandle();
            boolean hasMappedValue();
            float getMappedValue();
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

        private final ToFloatFunction<E> mMapper;

        private MappingExceptionHandler(Class<E> exceptionClass, ToFloatFunction<E> mapper) {
            super(exceptionClass);
            mMapper = mapper;
        }

        @Override
        protected Result performHandle(E exception) {
            return new ResultImpl(true, true, mMapper.apply(exception));
        }
    }

    private static class WrapperImpl implements FloatIterator, Wrapper {

        private final List<ExceptionHandler<?>> mExceptionHandlers = new ArrayList<>();
        private final FloatIterator mIterator;

        private boolean mEvaluated = false;
        private boolean mHasNext = false;
        private float mNext;

        private WrapperImpl(FloatIterator iterator) {
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
        public Float next() {
            return nextFloat();
        }

        boolean doesHandleExceptions() {
            return !mExceptionHandlers.isEmpty();
        }

        @Override
        public float nextFloat() {
            if (!doesHandleExceptions()) {
                return mIterator.nextFloat();
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
        public <E extends Throwable> void mapException(Class<E> exceptionClass, ToFloatFunction<E> mapper) {
            mExceptionHandlers.add(new MappingExceptionHandler<>(exceptionClass, mapper));
        }
    }

    private static class ResultImpl implements ExceptionHandler.Result {

        private final boolean mDoesHandle;
        private final boolean mHasMappedValue;
        private final float mMappedValue;

        private ResultImpl(boolean doesHandle, boolean hasMappedValue, float mappedValue) {
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
        public float getMappedValue() {
            return mMappedValue;
        }
    }
}
