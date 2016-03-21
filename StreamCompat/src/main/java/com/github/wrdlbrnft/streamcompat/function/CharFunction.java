package com.github.wrdlbrnft.streamcompat.function;

public interface CharFunction<R> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(char value);
}