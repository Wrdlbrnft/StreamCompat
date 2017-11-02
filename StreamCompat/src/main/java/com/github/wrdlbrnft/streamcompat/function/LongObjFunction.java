package com.github.wrdlbrnft.streamcompat.function;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 22/03/16
 */
public interface LongObjFunction<T, R> {
    R apply(long i, T t);
}
