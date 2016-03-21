package com.github.wrdlbrnft.streamcompat.function;

public interface ObjCharConsumer<T> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param value the second input argument
     */
    void accept(T t, char value);
}