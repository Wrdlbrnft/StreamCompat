package com.github.wrdlbrnft.streamcompat.function;

public interface Function<T, N> {
    N apply(T item);
}