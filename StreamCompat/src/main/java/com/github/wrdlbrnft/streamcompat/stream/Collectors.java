package com.github.wrdlbrnft.streamcompat.stream;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.function.BiConsumer;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.util.LongSparseArrayCompat;
import com.github.wrdlbrnft.streamcompat.util.MapCompat;
import com.github.wrdlbrnft.streamcompat.util.SparseArrayCompat;
import com.github.wrdlbrnft.streamcompat.util.StringJoiner;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by kapeller on 10/03/16.
 */
public class Collectors {

    public static <T> Collector<T, ?, List<T>> toList() {
        return new CollectorImpl<T, List<T>, List<T>>(
                ArrayList::new,
                List::add,
                (i) -> i
        );
    }

    public static <T, L extends List<T>> Collector<T, ?, L> toList(Supplier<L> listSupplier) {
        return new CollectorImpl<T, L, L>(
                listSupplier,
                List::add,
                (i) -> i
        );
    }

    public static <T> Collector<T, ?, List<T>> toOrdereredList(Comparator<T> comparator) {
        return new CollectorImpl<T, List<T>, List<T>>(
                ArrayList::new,
                List::add,
                list -> {
                    Collections.sort(list, comparator);
                    return list;
                }
        );
    }

    public static <T extends Comparable<T>> Collector<T, ?, List<T>> toOrdereredList() {
        return new CollectorImpl<T, List<T>, List<T>>(
                ArrayList::new,
                List::add,
                list -> {
                    Collections.sort(list);
                    return list;
                }
        );
    }

    public static <T> Collector<T, ?, Set<T>> toSet() {
        return new CollectorImpl<T, Set<T>, Set<T>>(
                HashSet::new,
                Set::add,
                i -> i
        );
    }

    public static <T, L extends Set<T>> Collector<T, ?, L> toSet(Supplier<L> setSupplier) {
        return new CollectorImpl<T, L, L>(
                setSupplier,
                Set::add,
                i -> i
        );
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                                                          Function<? super T, ? extends U> valueMapper,
                                                                          BinaryOperator<U> mergeFunction,
                                                                          Supplier<M> mapSupplier) {
        final BiConsumer<M, T> accumulator = (map, element) -> {
            final K key = keyMapper.apply(element);
            final U value = map.containsKey(key)
                    ? mergeFunction.apply(map.get(key), valueMapper.apply(element))
                    : valueMapper.apply(element);
            map.put(key, value);
        };
        return new CollectorImpl<>(mapSupplier, accumulator, i -> i);
    }

    public static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(Function<? super T, ? extends K> keyMapper,
                                                                          Function<? super T, ? extends U> valueMapper,
                                                                          Supplier<M> mapSupplier) {
        return toMap(keyMapper, valueMapper, throwingMerger(), mapSupplier);
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper,
                                                             Function<? super T, ? extends U> valueMapper,
                                                             BinaryOperator<U> mergeFunction) {
        return toMap(keyMapper, valueMapper, mergeFunction, ArrayMap::new);
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(Function<? super T, ? extends K> keyMapper,
                                                             Function<? super T, ? extends U> valueMapper) {
        return toMap(keyMapper, valueMapper, throwingMerger(), ArrayMap::new);
    }

    public static <T, U> Collector<T, ?, SparseArray<U>> toSparseArray(ToIntFunction<? super T> keyMapper,
                                                                       Function<? super T, ? extends U> valueMapper,
                                                                       BinaryOperator<U> mergeFunction) {
        final BiConsumer<SparseArray<U>, T> accumulator = (map, element) -> {
            final int key = keyMapper.apply(element);
            final U current = map.get(key);
            final U value = current != null
                    ? mergeFunction.apply(current, valueMapper.apply(element))
                    : valueMapper.apply(element);
            map.put(key, value);
        };
        return new CollectorImpl<>(SparseArray<U>::new, accumulator, i -> i);
    }

    public static <T, U> Collector<T, ?, SparseArray<U>> toSparseArray(ToIntFunction<? super T> keyMapper,
                                                                       Function<? super T, ? extends U> valueMapper) {
        return toSparseArray(keyMapper, valueMapper, throwingMerger());
    }

    public static <T, U> Collector<T, ?, LongSparseArray<U>> toLongSparseArray(ToLongFunction<? super T> keyMapper,
                                                                               Function<? super T, ? extends U> valueMapper,
                                                                               BinaryOperator<U> mergeFunction) {
        final BiConsumer<LongSparseArray<U>, T> accumulator = (map, element) -> {
            final long key = keyMapper.apply(element);
            final U current = map.get(key);
            final U value = current != null
                    ? mergeFunction.apply(current, valueMapper.apply(element))
                    : valueMapper.apply(element);
            map.put(key, value);
        };
        return new CollectorImpl<>(LongSparseArray<U>::new, accumulator, i -> i);
    }

    public static <T, U> Collector<T, ?, LongSparseArray<U>> toLongSparseArray(ToLongFunction<? super T> keyMapper,
                                                                               Function<? super T, ? extends U> valueMapper) {
        return toLongSparseArray(keyMapper, valueMapper, throwingMerger());
    }

    private static <U> BinaryOperator<U> throwingMerger() {
        return (t, u) -> {
            throw new IllegalStateException("Multiple values mapped to the same key");
        };
    }

    public static <T, K, D, A, M extends Map<K, D>> Collector<T, ?, M> groupingBy(
            Function<? super T, ? extends K> classifier,
            Supplier<M> mapFactory,
            Collector<? super T, A, D> downstream) {

        final Supplier<A> downstreamSupplier = downstream.supplier();
        final BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        final BiConsumer<Map<K, A>, T> accumulator = (m, t) -> {
            K key = Utils.requireNonNull(classifier.apply(t), "element cannot be mapped to a null key");
            A container = MapCompat.computeIfAbsent(m, key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };

        @SuppressWarnings("unchecked")
        final Supplier<Map<K, A>> mangledFactory = (Supplier<Map<K, A>>) mapFactory;

        @SuppressWarnings("unchecked")
        final Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
        final Function<Map<K, A>, M> finisher = intermediate -> {
            MapCompat.replaceAll(intermediate, (k, v) -> downstreamFinisher.apply(v));

            //noinspection unchecked
            return (M) intermediate;
        };
        return new CollectorImpl<>(mangledFactory, accumulator, finisher);
    }

    public static <T, K, A, D> Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier, Collector<? super T, A, D> downstream) {
        return groupingBy(classifier, ArrayMap::new, downstream);
    }

    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier) {
        return groupingBy(classifier, toList());
    }

    public static <T, A, D> Collector<T, ?, SparseArray<D>> groupingInSparseArray(ToIntFunction<? super T> classifier, Collector<? super T, A, D> downstream) {

        final Supplier<A> downstreamSupplier = downstream.supplier();
        final BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        final BiConsumer<SparseArray<A>, T> accumulator = (m, t) -> {
            final int key = classifier.apply(t);
            A container = SparseArrayCompat.computeIfAbsent(m, key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };

        @SuppressWarnings("unchecked")
        final Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
        final Function<SparseArray<A>, SparseArray<D>> finisher = intermediate -> {
            SparseArrayCompat.replaceAll(intermediate, (k, v) -> downstreamFinisher.apply(v));
            //noinspection unchecked
            return (SparseArray<D>) intermediate;
        };

        return new CollectorImpl<>(
                SparseArray::new,
                accumulator,
                finisher
        );
    }

    public static <T> Collector<T, ?, SparseArray<List<T>>> groupingInSparseArray(ToIntFunction<? super T> classifier) {
        return groupingInSparseArray(classifier, toList());
    }

    public static <T, A, D> Collector<T, ?, LongSparseArray<D>> groupingInLongSparseArray(ToLongFunction<? super T> classifier, Collector<? super T, A, D> downstream) {

        final Supplier<A> downstreamSupplier = downstream.supplier();
        final BiConsumer<A, ? super T> downstreamAccumulator = downstream.accumulator();
        final BiConsumer<LongSparseArray<A>, T> accumulator = (m, t) -> {
            final long key = classifier.apply(t);
            A container = LongSparseArrayCompat.computeIfAbsent(m, key, k -> downstreamSupplier.get());
            downstreamAccumulator.accept(container, t);
        };

        @SuppressWarnings("unchecked")
        final Function<A, A> downstreamFinisher = (Function<A, A>) downstream.finisher();
        final Function<LongSparseArray<A>, LongSparseArray<D>> finisher = intermediate -> {
            LongSparseArrayCompat.replaceAll(intermediate, (k, v) -> downstreamFinisher.apply(v));
            //noinspection unchecked
            return (LongSparseArray<D>) intermediate;
        };

        return new CollectorImpl<>(
                LongSparseArray::new,
                accumulator,
                finisher
        );
    }

    public static <T> Collector<T, ?, LongSparseArray<List<T>>> groupingInLongSparseArray(ToLongFunction<? super T> classifier) {
        return groupingInLongSparseArray(classifier, toList());
    }

    public static <T, A, R> Collector<T, A, R> create(Supplier<A> supplier, BiConsumer<A, T> accumulator, Function<A, R> finisher) {
        return new CollectorImpl<>(supplier, accumulator, finisher);
    }

    public static <T> Collector<T, ?, String> joining() {
        return new CollectorImpl<>(
                StringBuilder::new,
                StringBuilder::append,
                StringBuilder::toString
        );
    }

    public static <T> Collector<T, ?, String> joining(String delimiter) {
        return new CollectorImpl<>(
                () -> new StringJoiner(delimiter, "", ""),
                StringJoiner::add,
                StringJoiner::toString
        );
    }

    public static <T> Collector<T, ?, String> joining(String delimiter, String prefix, String suffix) {
        return new CollectorImpl<>(
                () -> new StringJoiner(delimiter, prefix, suffix),
                StringJoiner::add,
                StringJoiner::toString
        );
    }

    public static <T> Collector<T, ?, Collection<T>> appendTo(Collection<T> collection) {
        return new CollectorImpl<>(
                () -> collection,
                Collection::add,
                i -> i
        );
    }
}
