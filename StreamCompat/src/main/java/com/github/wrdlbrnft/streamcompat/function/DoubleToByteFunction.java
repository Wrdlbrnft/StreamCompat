package com.github.wrdlbrnft.streamcompat.function;

public interface DoubleToByteFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    byte applyAsByte(double value);
}