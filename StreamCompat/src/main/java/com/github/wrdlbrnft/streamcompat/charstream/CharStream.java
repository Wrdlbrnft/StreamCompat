package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.CharBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.function.CharPredicate;
import com.github.wrdlbrnft.streamcompat.function.CharToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.CharUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjCharConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.OptionalChar;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;

/**
 * Created by kapeller on 21/03/16.
 */
public interface CharStream extends Iterable<Character> {
    CharStream filter(CharPredicate predicate);
    CharStream map(CharUnaryOperator mapper);
    CharStream flatMap(CharFunction<? extends CharStream> mapper);
    <U> Stream<U> mapToObj(CharFunction<? extends U> mapper);
    LongStream mapToLong(CharToLongFunction mapper);
    IntStream mapToInt(CharToIntFunction mapper);
    DoubleStream mapToDouble(CharToDoubleFunction mapper);
    FloatStream mapToFloat(CharToFloatFunction mapper);
    Stream<Character> boxed();
    CharStream limit(long limit);
    char reduce(char identity, CharBinaryOperator op);
    OptionalChar reduce(CharBinaryOperator op);
    <R> R collect(Supplier<R> supplier, ObjCharConsumer<R> accumulator);
    char sum();
    OptionalChar min();
    OptionalChar max();
    long count();
    OptionalDouble average();
    OptionalChar findFirst();

    boolean anyMatch(CharPredicate predicate);
    boolean allMatch(CharPredicate predicate);
    boolean noneMatch(CharPredicate predicate);

    @Override
    CharIterator iterator();
}
