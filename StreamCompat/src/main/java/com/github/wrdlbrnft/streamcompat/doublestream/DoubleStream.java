package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.DoubleBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.DoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.DoublePredicate;
import com.github.wrdlbrnft.streamcompat.function.DoubleToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjDoubleConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;

/**
 * Created by kapeller on 21/03/16.
 */
public interface DoubleStream extends Iterable<Double> {
    DoubleStream filter(DoublePredicate predicate);
    DoubleStream map(DoubleUnaryOperator mapper);
    DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper);
    <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper);
    LongStream mapToLong(DoubleToLongFunction mapper);
    FloatStream mapToFloat(DoubleToFloatFunction mapper);
    IntStream mapToInt(DoubleToIntFunction mapper);
    CharacterStream mapToChar(DoubleToCharFunction mapper);
    Stream<Double> boxed();
    DoubleStream limit(long limit);
    double reduce(double identity, DoubleBinaryOperator op);
    OptionalDouble reduce(DoubleBinaryOperator op);
    <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator);
    double sum();
    OptionalDouble min();
    OptionalDouble max();
    long count();
    OptionalDouble average();
    OptionalDouble findFirst();

    boolean anyMatch(DoublePredicate predicate);
    boolean allMatch(DoublePredicate predicate);
    boolean noneMatch(DoublePredicate predicate);

    @Override
    DoubleIterator iterator();
}
