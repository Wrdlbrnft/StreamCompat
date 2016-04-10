package com.github.wrdlbrnft.streamcompat.function;

public interface ToByteFunction<T> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    byte apply(T value);
}