package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface DoubleIteratorWrapper extends BaseIteratorWrapper<DoubleIterator> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, ToDoubleFunction<E> mapper);
}
