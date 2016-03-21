package com.github.wrdlbrnft.streamcompat.function;

public interface ToFloatFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    float apply(T value);
}