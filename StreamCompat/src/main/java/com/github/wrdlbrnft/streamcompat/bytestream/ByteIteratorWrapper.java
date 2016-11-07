package com.github.wrdlbrnft.streamcompat.bytestream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;
import com.github.wrdlbrnft.streamcompat.function.ToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface ByteIteratorWrapper extends BaseIteratorWrapper<ByteIterator> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, ToByteFunction<E> mapper);
}
