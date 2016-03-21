package com.github.wrdlbrnft.streamcompat.function;

public interface FloatToDoubleFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(float value);
}