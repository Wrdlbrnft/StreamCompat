package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.iterator.LongArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class LongStreamCompat {

    public static LongStream of(long... values) {
        final LongIterator iterator = new LongArrayIterator(values);
        return new LongStreamImpl(iterator);
    }

    public static LongStream range(long start, long end) {
        if (end < start) {
            throw new IllegalArgumentException("End value must be bigger than start value!");
        }

        final LongIterator iterator = new LongRangeIterator(start, end);
        return new LongStreamImpl(iterator);
    }

    public static LongStream of(LongIterator iterator) {
        return new LongStreamImpl(iterator);
    }
}
