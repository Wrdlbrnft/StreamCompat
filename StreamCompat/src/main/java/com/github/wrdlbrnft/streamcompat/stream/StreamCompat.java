package com.github.wrdlbrnft.streamcompat.stream;

import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.sparsearray.LongSparseArrayValueIterator;
import com.github.wrdlbrnft.streamcompat.iterator.sparsearray.SparseArrayValueIterator;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by kapeller on 10/03/16.
 */
public class StreamCompat {

    private static final Stream<?> EMPTY_STREAM = new StreamImpl<>(Utils.emptyIterator());

    @SuppressWarnings("unchecked")
    public static <S> Stream<S> empty() {
        return (Stream<S>) EMPTY_STREAM;
    }

    @SafeVarargs
    public static <S> Stream<S> concat(Stream<S>... streams) {
        final Iterator<Stream<S>> iterator = new ArrayIterator<>(streams);
        @SuppressWarnings("unchecked")
        final Iterator<S>[] buffer = new Iterator[1];
        return new StreamImpl<>(new ChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return Utils.emptyIterator();
                        }
                        do {
                            buffer[0] = iterator.next().iterator();
                        } while(iterator.hasNext() && !buffer[0].hasNext());
                    }
                    return buffer[0];
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    public static <T> Stream<T> ofValues(LongSparseArray<T> array) {
        final Iterator<T> iterator = new LongSparseArrayValueIterator<>(array);
        return new StreamImpl<>(iterator);
    }

    public static <T> Stream<T> ofValues(SparseArray<T> array) {
        final Iterator<T> iterator = new SparseArrayValueIterator<>(array);
        return new StreamImpl<>(iterator);
    }

    public static <K, V> Stream<V> ofValues(Map<K, V> map) {
        final Iterator<V> iterator = map.values().iterator();
        return new StreamImpl<>(iterator);
    }

    public static <K, V> Stream<K> ofKeys(Map<K, V> map) {
        final Iterator<K> iterator = map.keySet().iterator();
        return new StreamImpl<>(iterator);
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
}
