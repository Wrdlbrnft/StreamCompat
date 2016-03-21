package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.charstream.CharStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.IntBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.function.IntToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.IntUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjIntConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.OptionalInt;

/**
 * Created by kapeller on 21/03/16.
 */
public interface IntStream extends Iterable<Integer> {
    IntStream filter(IntPredicate predicate);
    IntStream map(IntUnaryOperator mapper);
    IntStream flatMap(IntFunction<? extends IntStream> mapper);
    <U> Stream<U> mapToObj(IntFunction<? extends U> mapper);
    LongStream mapToLong(IntToLongFunction mapper);
    DoubleStream mapToDouble(IntToDoubleFunction mapper);
    FloatStream mapToFloat(IntToFloatFunction mapper);
    CharStream mapToChar(IntToCharFunction mapper);
    Stream<Integer> boxed();
    IntStream limit(long limit);
    int reduce(int identity, IntBinaryOperator op);
    OptionalInt reduce(IntBinaryOperator op);
    <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator);
    int sum();
    OptionalInt min();
    OptionalInt max();
    long count();
    OptionalDouble average();
    OptionalInt findFirst();

    boolean anyMatch(IntPredicate predicate);
    boolean allMatch(IntPredicate predicate);
    boolean noneMatch(IntPredicate predicate);

    @Override
    IntIterator iterator();
}
