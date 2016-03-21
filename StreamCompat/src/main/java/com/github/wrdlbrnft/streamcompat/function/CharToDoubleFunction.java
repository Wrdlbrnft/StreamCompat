package com.github.wrdlbrnft.streamcompat.function;

public interface CharToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(char value);
}