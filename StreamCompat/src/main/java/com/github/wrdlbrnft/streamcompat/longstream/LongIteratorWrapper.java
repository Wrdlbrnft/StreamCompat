package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface LongIteratorWrapper extends BaseIteratorWrapper<LongIterator> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, ToLongFunction<E> mapper);
}
