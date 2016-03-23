package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.function.FloatBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.FloatFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatPredicate;
import com.github.wrdlbrnft.streamcompat.function.FloatToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjFloatConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.OptionalFloat;

/**
 * Created by kapeller on 21/03/16.
 */
public interface FloatStream extends Iterable<Float> {
    FloatStream filter(FloatPredicate predicate);
    FloatStream map(FloatUnaryOperator mapper);
    FloatStream flatMap(FloatFunction<? extends FloatStream> mapper);
    <U> Stream<U> mapToObj(FloatFunction<? extends U> mapper);
    LongStream mapToLong(FloatToLongFunction mapper);
    IntStream mapToInt(FloatToIntFunction mapper);
    CharacterStream mapToChar(FloatToCharFunction mapper);
    DoubleStream mapToDouble(FloatToDoubleFunction mapper);
    Stream<Float> boxed();
    FloatStream limit(long limit);
    float reduce(float identity, FloatBinaryOperator op);
    OptionalFloat reduce(FloatBinaryOperator op);
    <R> R collect(Supplier<R> supplier, ObjFloatConsumer<R> accumulator);
    float sum();
    OptionalFloat min();
    OptionalFloat max();
    long count();
    OptionalFloat average();
    OptionalFloat findFirst();

    boolean anyMatch(FloatPredicate predicate);
    boolean allMatch(FloatPredicate predicate);
    boolean noneMatch(FloatPredicate predicate);

    @Override
    FloatIterator iterator();
}
