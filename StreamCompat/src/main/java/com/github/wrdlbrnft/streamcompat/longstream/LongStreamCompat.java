package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.LongArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
public class LongStreamCompat {

    static final LongIterator EMPTY_ITERATOR = new EmptyLongIterator();

    private static final LongStreamImpl EMPTY_STREAM = new LongStreamImpl(EMPTY_ITERATOR);

    public static LongStream empty() {
        return EMPTY_STREAM;
    }

    public static LongStream concat(LongStream... streams) {
        final Iterator<LongStream> iterator = new ArrayIterator<>(streams);
        final LongIterator[] buffer = new LongIterator[1];
        return new LongStreamImpl(new LongChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                LongIterator::hasNext,
                LongIterator::nextLong
        ));
    }

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

    private static class EmptyLongIterator extends EmptyIterator<Long> implements LongIterator {

        @Override
        public long nextLong() {
            throw new NoSuchElementException();
        }
    }
}
