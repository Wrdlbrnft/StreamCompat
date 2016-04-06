package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.DoubleBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.DoubleConsumer;
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
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code double} values.
 */
public interface DoubleStream extends Iterable<Double> {

    /**
     *
     * @param predicate
     * @return
     */
    DoubleStream filter(DoublePredicate predicate);

    /**
     *
     * @param mapper
     * @return
     */
    DoubleStream map(DoubleUnaryOperator mapper);

    /**
     *
     * @param mapper
     * @return
     */
    DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper);

    /**
     *
     * @param mapper
     * @param <U>
     * @return
     */
    <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper);

    /**
     *
     * @param mapper
     * @return
     */
    LongStream mapToLong(DoubleToLongFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    FloatStream mapToFloat(DoubleToFloatFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    IntStream mapToInt(DoubleToIntFunction mapper);

    /**
     *
     * @param mapper
     * @return
     */
    CharacterStream mapToChar(DoubleToCharFunction mapper);

    /**
     *
     * @return
     */
    Stream<Double> boxed();

    /**
     *
     * @param limit
     * @return
     */
    DoubleStream limit(long limit);

    /**
     *
     * @param identity
     * @param op
     * @return
     */
    double reduce(double identity, DoubleBinaryOperator op);

    /**
     *
     * @param op
     * @return
     */
    OptionalDouble reduce(DoubleBinaryOperator op);

    /**
     *
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator);

    /**
     *
     * @return
     */
    double sum();

    /**
     *
     * @return
     */
    OptionalDouble min();

    /**
     *
     * @return
     */
    OptionalDouble max();

    /**
     *
     * @return
     */
    long count();

    /**
     *
     * @return
     */
    OptionalDouble average();

    /**
     *
     * @return
     */
    OptionalDouble findFirst();

    /**
     *
     * @param predicate
     * @return
     */
    boolean anyMatch(DoublePredicate predicate);

    /**
     *
     * @param predicate
     * @return
     */
    boolean allMatch(DoublePredicate predicate);

    /**
     *
     * @param predicate
     * @return
     */
    boolean noneMatch(DoublePredicate predicate);

    /**
     *
     * @return
     */
    double[] toArray();

    /**
     *
     * @return
     */
    @Override
    DoubleIterator iterator();

    /**
     *
     * @param action
     */
    void forEach(DoubleConsumer action);
}
