package com.github.wrdlbrnft.streamcompat.function;

public interface FloatToLongFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLong(float value);
}