package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.iterator.IntArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class IntStreamCompat {

    public static IntStream of(int... values) {
        final IntIterator iterator = new IntArrayIterator(values);
        return new IntStreamImpl(iterator);
    }

    public static IntStream range(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("End value must be bigger than start value!");
        }

        final IntIterator iterator = new IntRangeIterator(start, end);
        return new IntStreamImpl(iterator);
    }

    public static IntStream of(IntIterator iterator) {
        return new IntStreamImpl(iterator);
    }
}
