package com.github.wrdlbrnft.streamcompat.function;

public interface ObjByteConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param value the second input argument
     */
    void accept(T t, byte value);
}