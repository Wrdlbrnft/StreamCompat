package com.github.wrdlbrnft.streamcompat.function;

public interface DoubleToLongFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLong(double value);
}