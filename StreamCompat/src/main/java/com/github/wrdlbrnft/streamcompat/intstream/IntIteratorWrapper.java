package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface IntIteratorWrapper extends BaseIteratorWrapper<IntIterator> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, ToIntFunction<E> mapper);
}
