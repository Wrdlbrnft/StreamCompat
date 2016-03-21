package com.github.wrdlbrnft.streamcompat.function;

public interface ToIntFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int apply(T value);
}