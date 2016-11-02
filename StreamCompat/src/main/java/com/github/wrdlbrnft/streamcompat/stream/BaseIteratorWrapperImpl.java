package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.exceptions.StreamException;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

class BaseIteratorWrapperImpl<T> implements IteratorWrapper<T> {

    private interface Wrapper<T> {
        <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer);
        <E extends Throwable> void mapException(Class<E> exceptionClass, Function<E, T> mapper);
    }

    private Wrapper<T> mWrapper;

    @Override
    public Iterator<T> apply(Iterator<T> iterator) {
        final WrapperImpl<T> wrapper = new WrapperImpl<>(iterator);
        mWrapper = wrapper;
        return wrapper;
    }

    @Override
    public <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer) {
        mWrapper.consumeException(exceptionClass, consumer);
    }

    @Override
    public <E extends Throwable> void mapException(Class<E> exceptionClass, Function<E, T> mapper) {
        mWrapper.mapException(exceptionClass, mapper);
    }

    private static abstract class ExceptionHandler<T, E extends Throwable> {

        interface Result<T> {
            boolean doesHandle();
            boolean hasMappedValue();
            T getMappedValue();
        }

        private final Class<E> mExceptionClass;

        private ExceptionHandler(Class<E> exceptionClass) {
            mExceptionClass = exceptionClass;
        }

        @SuppressWarnings("unchecked")
        Result<T> handle(Throwable exception) {
            if (!mExceptionClass.isAssignableFrom(exception.getClass())) {
                return new ResultImpl<>(false, false, null);
            }

            return performHandle((E) exception);
        }

        protected abstract Result<T> performHandle(E exception);
    }

    private static class ConsumeExceptionHandler<T, E extends Throwable> extends ExceptionHandler<T, E> {

        private final Consumer<E> mConsumer;

        private ConsumeExceptionHandler(Class<E> exceptionClass, Consumer<E> consumer) {
            super(exceptionClass);
            mConsumer = consumer;
        }

        @Override
        protected Result<T> performHandle(E exception) {
            mConsumer.accept(exception);
            return new ResultImpl<>(true, false, null);
        }
    }

    private static class MappingExceptionHandler<T, E extends Throwable> extends ExceptionHandler<T, E> {

        private final Function<E, T> mMapper;

        private MappingExceptionHandler(Class<E> exceptionClass, Function<E, T> mapper) {
            super(exceptionClass);
            mMapper = mapper;
        }

        @Override
        protected Result<T> performHandle(E exception) {
            return new ResultImpl<>(true, true, mMapper.apply(exception));
        }
    }

    private static class WrapperImpl<T> implements Iterator<T>, Wrapper<T> {

        private final List<ExceptionHandler<T, ?>> mExceptionHandlers = new ArrayList<>();
        private final Iterator<T> mIterator;

        private boolean mEvaluated = false;
        private boolean mHasNext = false;
        private T mNext;

        private WrapperImpl(Iterator<T> iterator) {
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
        public T next() {
            if (!doesHandleExceptions()) {
                return mIterator.next();
            }
            evaluate();
            mEvaluated = false;
            if (mHasNext) {
                return mNext;
            }
            throw new NoSuchElementException();
        }

        boolean doesHandleExceptions() {
            return !mExceptionHandlers.isEmpty();
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
                    for (ExceptionHandler<T, ?> handler : mExceptionHandlers) {
                        final ExceptionHandler.Result<T> result = handler.handle(e);
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
        public <E extends Throwable> void mapException(Class<E> exceptionClass, Function<E, T> mapper) {
            mExceptionHandlers.add(new MappingExceptionHandler<>(exceptionClass, mapper));
        }
    }

    private static class ResultImpl<T> implements ExceptionHandler.Result<T> {

        private final boolean mDoesHandle;
        private final boolean mHasMappedValue;
        private final T mMappedValue;

        private ResultImpl(boolean doesHandle, boolean hasMappedValue, T mappedValue) {
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
        public T getMappedValue() {
            return mMappedValue;
        }
    }
}
