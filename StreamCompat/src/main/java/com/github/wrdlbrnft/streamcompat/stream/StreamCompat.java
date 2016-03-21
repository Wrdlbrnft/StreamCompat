package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.iterator.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongArrayIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
public class StreamCompat {

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
