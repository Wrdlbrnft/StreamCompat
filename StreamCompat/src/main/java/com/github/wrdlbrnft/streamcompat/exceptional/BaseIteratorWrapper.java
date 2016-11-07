package com.github.wrdlbrnft.streamcompat.exceptional;

import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;

import java.util.Iterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */
public interface BaseIteratorWrapper<I extends Iterator<?>> {
    I apply(I iterator);
    <E extends Throwable> void consumeException(Class<E> exceptionClass, Consumer<E> consumer);
}
