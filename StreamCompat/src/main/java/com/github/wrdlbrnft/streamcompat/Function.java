package com.github.wrdlbrnft.streamcompat;

public interface Function<T, N> {
    N apply(T item);
}