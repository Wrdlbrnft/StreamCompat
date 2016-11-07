package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseIteratorWrapper;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

interface CharacterIteratorWrapper extends BaseIteratorWrapper<CharIterator> {
    <E extends Throwable> void mapException(Class<E> exceptionClass, ToCharFunction<E> mapper);
}
