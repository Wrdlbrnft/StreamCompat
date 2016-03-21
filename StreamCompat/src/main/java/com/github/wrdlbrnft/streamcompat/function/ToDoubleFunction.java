package com.github.wrdlbrnft.streamcompat.function;

public interface ToDoubleFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double apply(T value);
}