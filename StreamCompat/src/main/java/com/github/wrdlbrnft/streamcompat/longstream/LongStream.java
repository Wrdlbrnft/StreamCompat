package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.LongBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.LongFunction;
import com.github.wrdlbrnft.streamcompat.function.LongPredicate;
import com.github.wrdlbrnft.streamcompat.function.LongToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.LongUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjLongConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.OptionalLong;

/**
 * Created by kapeller on 21/03/16.
 */
public interface LongStream extends Iterable<Long> {
    LongStream filter(LongPredicate predicate);
    LongStream map(LongUnaryOperator mapper);
    LongStream flatMap(LongFunction<? extends LongStream> mapper);
    <U> Stream<U> mapToObj(LongFunction<? extends U> mapper);
    IntStream mapToInt(LongToIntFunction mapper);
    DoubleStream mapToDouble(LongToDoubleFunction mapper);
    FloatStream mapToFloat(LongToFloatFunction mapper);
    CharacterStream mapToChar(LongToCharFunction mapper);
    Stream<Long> boxed();
    LongStream limit(long limit);
    long reduce(long identity, LongBinaryOperator op);
    OptionalLong reduce(LongBinaryOperator op);
    <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator);
    long sum();
    OptionalLong min();
    OptionalLong max();
    long count();
    OptionalDouble average();
    OptionalLong findFirst();

    boolean anyMatch(LongPredicate predicate);
    boolean allMatch(LongPredicate predicate);
    boolean noneMatch(LongPredicate predicate);

    @Override
    LongIterator iterator();
}
