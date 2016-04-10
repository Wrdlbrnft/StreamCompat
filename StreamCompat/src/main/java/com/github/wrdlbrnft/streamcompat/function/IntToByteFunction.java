package com.github.wrdlbrnft.streamcompat.function;

public interface IntToByteFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    byte applyAsByte(int value);
}