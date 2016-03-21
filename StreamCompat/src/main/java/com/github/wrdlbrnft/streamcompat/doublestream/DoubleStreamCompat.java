package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.iterator.DoubleArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class DoubleStreamCompat {

    public static DoubleStream of(double... values) {
        final DoubleIterator iterator = new DoubleArrayIterator(values);
        return new DoubleStreamImpl(iterator);
    }

    public static DoubleStream of(DoubleIterator iterator) {
        return new DoubleStreamImpl(iterator);
    }
}
