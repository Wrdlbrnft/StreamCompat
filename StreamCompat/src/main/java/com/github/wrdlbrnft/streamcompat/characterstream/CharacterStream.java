package com.github.wrdlbrnft.streamcompat.characterstream;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.CharBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.CharConsumer;
import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.function.CharPredicate;
import com.github.wrdlbrnft.streamcompat.function.CharToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.CharUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjCharConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalCharacter;
import com.github.wrdlbrnft.streamcompat.stream.Stream;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code char} values.
 */
public interface CharacterStream extends Iterable<Character> {

    /**
     * @param predicate
     * @return
     */
    CharacterStream filter(CharPredicate predicate);

    /**
     * @param mapper
     * @return
     */
    CharacterStream map(CharUnaryOperator mapper);

    /**
     * @param mapper
     * @return
     */
    CharacterStream flatMap(CharFunction<? extends CharacterStream> mapper);

    /**
     * @param mapper
     * @param <U>
     * @return
     */
    <U> Stream<U> mapToObj(CharFunction<? extends U> mapper);

    /**
     * @param mapper
     * @return
     */
    LongStream mapToLong(CharToLongFunction mapper);

    /**
     * @param mapper
     * @return
     */
    IntStream mapToInt(CharToIntFunction mapper);

    /**
     * @param mapper
     * @return
     */
    DoubleStream mapToDouble(CharToDoubleFunction mapper);

    /**
     * @param mapper
     * @return
     */
    FloatStream mapToFloat(CharToFloatFunction mapper);

    /**
     * @param mapper
     * @return
     */
    ByteStream mapToByte(CharToByteFunction mapper);

    /**
     * @return
     */
    Stream<Character> boxed();

    /**
     * @param limit
     * @return
     */
    CharacterStream limit(long limit);

    /**
     * @param count
     * @return
     */
    CharacterStream skip(long count);

    /**
     * @param identity
     * @param op
     * @return
     */
    char reduce(char identity, CharBinaryOperator op);

    /**
     * @param op
     * @return
     */
    OptionalCharacter reduce(CharBinaryOperator op);

    /**
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjCharConsumer<R> accumulator);

    <E extends Throwable> CharacterExceptional<E> exception(Class<E> cls);

    /**
     * @return
     */
    OptionalCharacter min();

    /**
     * @return
     */
    OptionalCharacter max();

    /**
     * @return
     */
    long count();

    /**
     * @return
     */
    CharacterStream sort();

    /**
     * @return
     */
    OptionalCharacter findFirst();

    /**
     * @param predicate
     * @return
     */
    boolean anyMatch(CharPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean allMatch(CharPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean noneMatch(CharPredicate predicate);

    /**
     * @return
     */
    char[] toArray();

    /**
     * @return
     */
    @NonNull
    @Override
    CharIterator iterator();

    /**
     * @param action
     */
    void forEach(CharConsumer action);
}
