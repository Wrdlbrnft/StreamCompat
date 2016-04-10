package com.github.wrdlbrnft.streamcompat.function;

public interface CharToByteFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    byte applyAsByte(char value);
}