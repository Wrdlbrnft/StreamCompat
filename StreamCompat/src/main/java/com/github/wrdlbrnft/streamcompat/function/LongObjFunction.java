package com.github.wrdlbrnft.streamcompat.function;

/**
 * Created by kapeller on 22/03/16.
 */
public interface LongObjFunction<T, R> {
    R apply(long i, T t);
}
