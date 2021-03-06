package com.github.wrdlbrnft.streamcompat.function;

public interface ToLongFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long apply(T value);
}