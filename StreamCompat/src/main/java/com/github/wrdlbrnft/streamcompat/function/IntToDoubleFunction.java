package com.github.wrdlbrnft.streamcompat.function;

public interface IntToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(int value);
}