package com.github.wrdlbrnft.streamcompat.function;

public interface ToCharFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    char apply(T value);
}