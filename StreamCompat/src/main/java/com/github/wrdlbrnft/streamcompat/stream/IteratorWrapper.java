package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;

import java.util.Iterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface IteratorWrapper<T> extends BaseIteratorWrapper<Iterator<T>> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, Function<E, T> mapper);
}
