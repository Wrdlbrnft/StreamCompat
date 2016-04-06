package com.github.wrdlbrnft.streamcompat.stream;

import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.collections.ArraySet;
import com.github.wrdlbrnft.streamcompat.collections.compat.LongSparseArrayCompat;
import com.github.wrdlbrnft.streamcompat.collections.compat.MapCompat;
import com.github.wrdlbrnft.streamcompat.collections.compat.SparseArrayCompat;
import com.github.wrdlbrnft.streamcompat.function.BiConsumer;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.util.StringJoiner;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class contains many predefined {@link Collector} implementations which provide many useful
 * reduction operations like accumulating elements into {@link Collection Collections} or
 * {@link Map Maps}.
 */
public class Collectors {

    /**
     * Returns a {@link Collector} which accumulates the elements in a {@link Stream} into an array.
     *
     * @param arrayGenerator A generator function which creates a new array with the specified length.
     * @param <T>            The type of the elements in the {@link Stream}.
     * @return Returns a new {@link Collector} with the supplied generator function.
     */
    public static <T> Collector<T, ?, T[]> toArray(IntFunction<T[]> arrayGenerator) {
        return new CollectorImpl<T, List<T>, T[]>(
                ArrayList::new,
                List::add,
                list -> {
                    final T[] array = arrayGenerator.apply(list.size());
                    return list.toArray(array);
                }
        );
    }

    /**
     * Creates a {@link Collector} which accumulates the elements from a {@link Stream} into a
     * {@link List}.
     *
     * @param <T> The type of the elements in the {@link Stream}
     * @return Returns a new {@link Collector} which creates a {@link List} with the elements in
     * a {@link Stream}.
     */
    public static <T> Collector<T, ?, List<T>> toList() {
        return toList(ArrayList::new);
    }

    /**
     * Creates a {@link Collector} which accumulates the elements from a {@link Stream} into the
     * {@link List} provided by the {@link Supplier listSupplier}.
     *
     * @param listSupplier A function which creates a new {@link List} of the desired type.
     * @param <T>          The type of the elements in the {@link Stream}.
     * @param <L>          The type of the {@link List} provided by the {@link Supplier listSupplier}.
     * @return Returns a new {@link Collector} which creates a {@link List} of the desired type with
     * the elements in a {@link Stream}.
     */
    public static <T, L extends List<T>> Collector<T, ?, L> toList(Supplier<L> listSupplier) {
        return new CollectorImpl<T, L, L>(
                listSupplier,
                L::add,
                (i) -> i
        );
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link List} and at the same time sorts them according to the supplied {@link Comparator}.
     *
     * @param comparator A {@link Comparator} used to compare and sort the elements from the
     *                   {@link Stream}.
     * @param <T>        The type of the elements in the {@link Stream}.
     * @return Returns a new {@link Collector} which creates an ordered {@link List} based on the
     * supplied {@link Comparator} with elements from a {@link Stream}.
     */
    public static <T> Collector<T, ?, List<T>> toOrderedList(Comparator<T> comparator) {
        return toOrderedList(ArrayList::new, comparator);
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link List} and at the same time sorts them according to the supplied {@link Comparator}.
     * The {@link List} is created by the {@link Supplier listSupplier}.
     *
     * @param listSupplier Creates a {@link List} of the desired type.
     * @param comparator   A {@link Comparator} used to compare and sort the elements from the
     *                     {@link Stream}.
     * @param <T>          The type of the elements in the {@link Stream}.
     * @param <L>          The type of the {@link List} created by the {@link Supplier listSupplier}.
     * @return Returns a new {@link Collector} which creates an ordered {@link List} of the desired
     * type based on the supplied {@link Comparator} with the elements from a {@link Stream}.
     */
    public static <T, L extends List<T>> Collector<T, ?, L> toOrderedList(Supplier<L> listSupplier, Comparator<T> comparator) {
        return new CollectorImpl<T, L, L>(
                listSupplier,
                L::add,
                list -> {
                    Collections.sort(list, comparator);
                    return list;
                }
        );
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link List} and at the same type sorts them according to their natural order. The items in
     * the {@link Stream} have to implement {@link Comparable} for this {@link Collector} to work.
     *
     * @param <T> The type of the elements in the {@link Stream}. Must extend {@link Comparable}.
     * @return Returns a new {@link Collector} which creates a new ordered {@link List} based on the
     * natural order of the elements from a {@link Stream}.
     */
    public static <T extends Comparable<T>> Collector<T, ?, List<T>> toOrderedList() {
        return toOrderedList(ArrayList::new);
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link List} of the desired type and at the same type sorts them according to their natural
     * order. The items in the {@link Stream} have to implement {@link Comparable} for this
     * {@link Collector} to work.
     *
     * @param listSupplier A function which creates a new {@link List} of the desired type.
     * @param <T>          The type of the elements in the {@link Stream}. Must extends {@link Comparable}.
     * @param <L>          The type of the {@link List} created by the {@link Supplier listSupplier}.
     * @return Returns a new {@link Collector} which creates a new ordered {@link List} of the
     * desired type based on the natural order of the elements from a {@link Stream}.
     */
    public static <T extends Comparable<T>, L extends List<T>> Collector<T, ?, L> toOrderedList(Supplier<L> listSupplier) {
        return new CollectorImpl<T, L, L>(
                listSupplier,
                List::add,
                list -> {
                    Collections.sort(list);
                    return list;
                }
        );
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link Set}.
     *
     * @param <T> The type of the elements in the {@link Stream}.
     * @return Returns a new {@link Collector} which creates a {@link Set} from the elements of a
     * {@link Stream}.
     */
    public static <T> Collector<T, ?, Set<T>> toSet() {
        return toSet(ArraySet::new);
    }

    /**
     * Creates a {@link Collector} which accumulates elements from a {@link Stream} into a
     * {@link Set} of the desired type.
     *
     * @param setSupplier A function which creates a new {@link Set} of the desired type.
     * @param <T> The type of the elements in the {@link Stream}.
     * @param <S> The type of the {@link Set} created by the {@link Supplier setSupplier}.
     * @return Returns a new {@link Collector} which creates a {@link Set} of the desired type from
     * the elements of a {@link Stream}.
     */
    public static <T, S extends Set<T>> Collector<T, ?, S> toSet(Supplier<S> setSupplier) {
        return new CollectorImpl<T, S, S>(
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
}
