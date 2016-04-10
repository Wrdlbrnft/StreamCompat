package com.github.wrdlbrnft.streamcompat.function;

public interface ByteToFloatFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    float applyAsFloat(byte value);
}