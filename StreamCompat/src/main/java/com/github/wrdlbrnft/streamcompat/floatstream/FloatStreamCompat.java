package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class FloatStreamCompat {

    public static FloatStream of(float... values) {
        final FloatIterator iterator = new FloatArrayIterator(values);
        return new FloatStreamImpl(iterator);
    }

    public static FloatStream of(FloatIterator iterator) {
        return new FloatStreamImpl(iterator);
    }
}
