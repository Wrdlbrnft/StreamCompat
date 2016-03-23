package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.DoubleArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.FloatArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.IntArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.LongArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
public class StreamCompat {

    private static final Stream<?> EMPTY_STREAM = new StreamImpl<>(Collections.emptyList().iterator());

    @SuppressWarnings("unchecked")
    public static <S> Stream<S> empty() {
        return (Stream<S>) EMPTY_STREAM;
    }

    @SafeVarargs
    public static <S> Stream<S> concat(Stream<S>... streams) {
        final Iterator<Stream<S>> iterator = new ArrayIterator<>(streams);
        final Iterator<S>[] buffer = new Iterator[1];
        return new StreamImpl<>(new ChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return Utils.emptyIterator();
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    public static <S> Stream<S> of(Iterable<S> collection) {
        final Iterator<S> iterator = new ImmutableIterator<>(collection.iterator());
        return new StreamImpl<>(iterator);
    }

    public static <S> Stream<S> of(Iterator<S> iterator) {
        return new StreamImpl<>(iterator);
    }

    @SafeVarargs
    public static <S> Stream<S> of(S... items) {
        final Iterator<S> iterator = new ArrayIterator<>(items);
        return new StreamImpl<>(iterator);
    }

    public static Stream<Character> of(char[] items) {
        final Iterator<Character> iterator = new CharArrayIterator(items);
        return new StreamImpl<>(iterator);
    }

    public static Stream<Integer> of(int[] items) {
        final Iterator<Integer> iterator = new IntArrayIterator(items);
        return new StreamImpl<>(iterator);
    }

    public static Stream<Long> of(long[] items) {
        final Iterator<Long> iterator = new LongArrayIterator(items);
        return new StreamImpl<>(iterator);
    }

    public static Stream<Float> of(float[] items) {
        final Iterator<Float> iterator = new FloatArrayIterator(items);
        return new StreamImpl<>(iterator);
    }

    public static Stream<Double> of(double[] items) {
        final Iterator<Double> iterator = new DoubleArrayIterator(items);
        return new StreamImpl<>(iterator);
    }
}
