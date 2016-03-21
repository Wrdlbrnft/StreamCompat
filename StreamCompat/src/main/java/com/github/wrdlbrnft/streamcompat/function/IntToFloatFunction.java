package com.github.wrdlbrnft.streamcompat.function;

public interface IntToFloatFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    float applyAsFloat(int value);
}