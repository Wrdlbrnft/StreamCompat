package com.github.wrdlbrnft.streamcompat.intstream;

import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.IntArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.sparsearray.SparseArrayKeyIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
public class IntStreamCompat {

    static final IntIterator EMPTY_ITERATOR = new EmptyIntIterator();

    private static final IntStreamImpl EMPTY_STREAM = new IntStreamImpl(EMPTY_ITERATOR);

    public static IntStream empty() {
        return EMPTY_STREAM;
    }

    public static IntStream concat(IntStream... streams) {
        final Iterator<IntStream> iterator = new ArrayIterator<>(streams);
        final IntIterator[] buffer = new IntIterator[1];
        return new IntStreamImpl(new IntChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
    }

    public static IntStream of(int... values) {
        final IntIterator iterator = new IntArrayIterator(values);
        return new IntStreamImpl(iterator);
    }

    public static <T> IntStream ofKeys(SparseArray<T> array) {
        final IntIterator iterator = new SparseArrayKeyIterator<>(array);
        return new IntStreamImpl(iterator);
    }

    public static IntStream range(int start, int end) {
        if (end < start) {
            throw new IllegalArgumentException("End value must be bigger than start value!");
        }

        final IntIterator iterator = new IntRangeIterator(start, end);
        return new IntStreamImpl(iterator);
    }

    public static IntStream positiveIntegers() {
        final IntIterator iterator = new IntRangeIterator(0, Integer.MAX_VALUE);
        return new IntStreamImpl(iterator);
    }

    public static IntStream negativeIntegers() {
        final IntIterator iterator = new IntRangeIterator(0, Integer.MIN_VALUE);
        return new IntStreamImpl(iterator);
    }

    public static IntStream of(IntIterator iterator) {
        return new IntStreamImpl(iterator);
    }

    private static class EmptyIntIterator extends EmptyIterator<Integer> implements IntIterator {

        @Override
        public int nextInt() {
            throw new NoSuchElementException();
        }
    }
}
