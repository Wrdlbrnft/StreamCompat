package com.github.wrdlbrnft.streamcompat.function;

public interface FloatToIntFunction {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(float value);
}