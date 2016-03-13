package com.github.wrdlbrnft.streamcompat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public static <T> Collector<T, ?, Collection<T>> append(Collection<T> collection) {
        return new CollectorImpl<>(
                () -> collection,
                Collection::add,
                i -> i
        );
    }
}
