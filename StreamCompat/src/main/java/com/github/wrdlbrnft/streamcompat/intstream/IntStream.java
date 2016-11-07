package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.IntBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.IntConsumer;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.function.IntToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.IntUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjIntConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalInt;
import com.github.wrdlbrnft.streamcompat.stream.Stream;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code int} values.
 */
public interface IntStream extends Iterable<Integer> {

    /**
     * @param predicate
     * @return
     */
    IntStream filter(IntPredicate predicate);

    /**
     * @param mapper
     * @return
     */
    IntStream map(IntUnaryOperator mapper);

    /**
     * @param mapper
     * @return
     */
    IntStream flatMap(IntFunction<? extends IntStream> mapper);

    /**
     * @param mapper
     * @param <U>
     * @return
     */
    <U> Stream<U> mapToObj(IntFunction<? extends U> mapper);

    /**
     * @param mapper
     * @return
     */
    LongStream mapToLong(IntToLongFunction mapper);

    /**
     * @param mapper
     * @return
     */
    DoubleStream mapToDouble(IntToDoubleFunction mapper);

    /**
     * @param mapper
     * @return
     */
    FloatStream mapToFloat(IntToFloatFunction mapper);

    /**
     * @param mapper
     * @return
     */
    CharacterStream mapToChar(IntToCharFunction mapper);

    /**
     * @param mapper
     * @return
     */
    ByteStream mapToByte(IntToByteFunction mapper);

    /**
     * @return
     */
    Stream<Integer> boxed();

    /**
     * @param maxSize
     * @return
     */
    IntStream limit(long maxSize);

    /**
     * @param count
     * @return
     */
    IntStream skip(long count);

    /**
     * @param identity
     * @param op
     * @return
     */
    int reduce(int identity, IntBinaryOperator op);

    /**
     * @param op
     * @return
     */
    OptionalInt reduce(IntBinaryOperator op);

    /**
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator);

    <E extends Throwable> IntExceptional<E> exception(Class<E> cls);

    /**
     * @return
     */
    int sum();

    /**
     * @return
     */
    OptionalInt min();

    /**
     * @return
     */
    OptionalInt max();

    /**
     * @return
     */
    long count();

    /**
     * @return
     */
    IntStream sort();

    /**
     * @return
     */
    OptionalDouble average();

    /**
     * @return
     */
    OptionalInt findFirst();

    /**
     * @param predicate
     * @return
     */
    boolean anyMatch(IntPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean allMatch(IntPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean noneMatch(IntPredicate predicate);

    /**
     * @return
     */
    int[] toArray();

    /**
     * @return
     */
    IntIterator iterator();

    /**
     * @param action
     */
    void forEach(IntConsumer action);
}
