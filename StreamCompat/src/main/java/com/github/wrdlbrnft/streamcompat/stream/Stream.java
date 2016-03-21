package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.charstream.CharStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.util.Optional;

import java.util.Comparator;

/**
 * Created by kapeller on 10/03/16.
 */
public interface Stream<T> extends Iterable<T> {
    Stream<T> filter(Predicate<T> predicate);
    <R> Stream<R> map(Function<T, ? extends R> mapper);
    IntStream mapToInt(ToIntFunction<? super T> mapper);
    LongStream mapToLong(ToLongFunction<? super T> mapper);
    DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper);
    FloatStream mapToFloat(ToFloatFunction<? super T> mapper);
    CharStream mapToChar(ToCharFunction<? super T> mapper);
    <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper);
    IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper);
    LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper);
    FloatStream flatMapToFloat(Function<? super T, ? extends FloatStream> mapper);
    DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper);
    CharStream flatMapToChar(Function<? super T, ? extends CharStream> mapper);
    <A, R> R collect(Collector<T, A, R> function);
    Stream<T> limit(long count);
    Optional<T> reduce(BinaryOperator<T> accumulator);
    T reduce(T identity, BinaryOperator<T> accumulator);
    Optional<T> min(Comparator<? super T> comparator);
    Optional<T> max(Comparator<? super T> comparator);
    long count();
    boolean anyMatch(Predicate<? super T> predicate);
    boolean allMatch(Predicate<? super T> predicate);
    boolean noneMatch(Predicate<? super T> predicate);
    Optional<T> findFirst();
}
