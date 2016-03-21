package com.github.wrdlbrnft.streamcompat.function;

public interface DoubleToCharFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    char applyAsChar(double value);
}