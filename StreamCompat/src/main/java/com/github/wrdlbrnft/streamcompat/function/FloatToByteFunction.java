package com.github.wrdlbrnft.streamcompat.function;

public interface FloatToByteFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
     byte applyAsByte(float value);
}