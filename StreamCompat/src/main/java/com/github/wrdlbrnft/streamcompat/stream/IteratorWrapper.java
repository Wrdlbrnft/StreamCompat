package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;

import java.util.Iterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */
interface IteratorWrapper<T> {
    Iterator<T> apply(Iterator<T> iterator);
    <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer);
    <E extends Throwable> void mapException(Class<E> exceptionClass, Function<E, T> mapper);
}
