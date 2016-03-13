package com.github.wrdlbrnft.streamcompat;

/**
 * Created by kapeller on 10/03/16.
 */
public interface Stream<T> extends Iterable<T> {
    Stream<T> filter(Predicate<T> predicate);
    <R> Stream<R> map(Function<T, ? extends R> mapper);
    <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper);
    <A, R> R collect(Collector<T, A, R> function);
}
