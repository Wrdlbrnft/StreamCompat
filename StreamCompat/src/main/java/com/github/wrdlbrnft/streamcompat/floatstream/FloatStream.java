package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.function.FloatBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.FloatConsumer;
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
import com.github.wrdlbrnft.streamcompat.optionals.OptionalFloat;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code float} values.
 */
public interface FloatStream extends Iterable<Float> {

    /**
     *
     * @param predicate
     * @return
     */
    FloatStream filter(FloatPredicate predicate);

    /**
     *
     * @param mapper
     * @return
     */
    FloatStream map(FloatUnaryOperator mapper);

    /**
     *
     * @param mapper
     * @return
     */
    FloatStream flatMap(FloatFunction<? extends FloatStream> mapper);

    /**
     *
     * @param mapper
     * @param <U>
     * @return
     */
    <U> Stream<U> mapToObj(FloatFunction<? extends U> mapper);

    /**
     *
     * @param mapper
     * @return
     */
    LongStream mapToLong(FloatToLongFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    IntStream mapToInt(FloatToIntFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    CharacterStream mapToChar(FloatToCharFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    DoubleStream mapToDouble(FloatToDoubleFunction mapper);

    /**
     *
     * @return
     */
    Stream<Float> boxed();

    /**
     *
     * @param limit
     * @return
     */
    FloatStream limit(long limit);

    /**
     *
     * @param identity
     * @param op
     * @return
     */
    float reduce(float identity, FloatBinaryOperator op);

    /**
     *
     * @param op
     * @return
     */
    OptionalFloat reduce(FloatBinaryOperator op);

    /**
     *
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjFloatConsumer<R> accumulator);

    /**
     *
     * @return
     */
    float sum();

    /**
     *
     * @return
     */
    OptionalFloat min();

    /**
     *
     * @return
     */
    OptionalFloat max();

    /**
     *
     * @return
     */
    long count();

    /**
     *
     * @return
     */
    OptionalFloat average();

    /**
     *
     * @return
     */
    OptionalFloat findFirst();

    /**
     *
     * @param predicate
     * @return
     */
    boolean anyMatch(FloatPredicate predicate);

    /**
     *
     * @param predicate
     * @return
     */
    boolean allMatch(FloatPredicate predicate);

    /**
     *
     * @param predicate
     * @return
     */
    boolean noneMatch(FloatPredicate predicate);

    /**
     *
     * @return
     */
    float[] toArray();

    /**
     *
     * @return
     */
    @Override
    FloatIterator iterator();


    /**
     *
     * @param action
     */
    void forEach(FloatConsumer action);
}
