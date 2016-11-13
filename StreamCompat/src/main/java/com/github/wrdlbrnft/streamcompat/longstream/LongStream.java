package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.LongBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.LongConsumer;
import com.github.wrdlbrnft.streamcompat.function.LongFunction;
import com.github.wrdlbrnft.streamcompat.function.LongPredicate;
import com.github.wrdlbrnft.streamcompat.function.LongToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.LongToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.LongUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjLongConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalLong;
import com.github.wrdlbrnft.streamcompat.stream.Stream;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code long} values.
 */
public interface LongStream extends Iterable<Long> {

    /**
     * Filters the long values in the {@link LongStream} according to the supplied {@link LongPredicate}.
     *
     * @param predicate A {@link LongPredicate} used to test the long values in the {@link LongStream}.
     * @return Returns a new {@link LongStream} which only contains the long values which match the
     * {@link LongPredicate}.
     */
    LongStream filter(LongPredicate predicate);

    /**
     * Maps each {@code long} value to another {@code long} value based
     * on the supplied mapper function.
     *
     * @param mapper A mapping function used to map the {@code long} values in the {@link LongStream}.
     * @return Returns a new {@link LongStream} which contains all the mapped {@code long} values.
     */
    LongStream map(LongUnaryOperator mapper);

    /**
     * Maps each {@code long} value to a new {@link LongStream} based on the supplied mapper
     * function. The {@code long} values in each of the mapped
     * {@link LongStream LongStreams} are combined into one big {@link LongStream}.
     *
     * @param mapper A mapping function used to map the {@code long} values to a {@link LongStream}.
     * @return Returns a new {@link LongStream} which contains all the {@code long} values of all the
     * mapped {@link LongStream LongStreams}.
     */
    LongStream flatMap(LongFunction<? extends LongStream> mapper);

    /**
     * Maps each {@code long} value to an object based on the supplied mapping function.
     *
     * @param mapper A function which maps the {@code long} values to an object.
     * @param <U>    The type of the Object the {@code long} values are being mapped to.
     * @return Returns a new {@link Stream} which contains all the mapped objects.
     */
    <U> Stream<U> mapToObj(LongFunction<? extends U> mapper);

    /**
     * Maps each {@code long} value to an {@code int} value based on the supplied mapping function.
     *
     * @param mapper A function which maps {@code long} values to {@code int} values.
     * @return Returns a new {@link IntStream} which contains all the mapped {@code int} values.
     */
    IntStream mapToInt(LongToIntFunction mapper);

    /**
     * Maps each {@code long} value to a {@code double} value based on th supplied mapping function.
     *
     * @param mapper A function which maps {@code long} values to {@code double} values.
     * @return Returns a new {@link DoubleStream} which contains all the mapped {@code double} values.
     */
    DoubleStream mapToDouble(LongToDoubleFunction mapper);

    /**
     * Maps each {@code long} value to a {@code float} value based on the supplied mapping function.
     *
     * @param mapper A function which maps {@code long} values to {@code float} values.
     * @return Returns a new {@link FloatStream} which contains all the mapped {@code float} values.
     */
    FloatStream mapToFloat(LongToFloatFunction mapper);

    /**
     * Maps each {@code long} value to a {@code char} values based on the supplied mapping function.
     *
     * @param mapper A function which maps {@code long} values to {@code char} values.
     * @return Returns a new {@link CharacterStream} which contains all the mapped {@code char} values.
     */
    CharacterStream mapToChar(LongToCharFunction mapper);

    /**
     * Maps each {@code long} value to a {@code byte} values based on the supplied mapping function.
     *
     * @param mapper A function which maps {@code long} values to {@code byte} values.
     * @return Returns a new {@link ByteStream} which contains all the mapped {@code byte} values.
     */
    ByteStream mapToByte(LongToByteFunction mapper);

    /**
     * Boxes each primitive {@code long} value in the {@link LongStream} by calling {@link Long#valueOf(long)}.
     *
     * @return Returns a new {@link Stream} which contains the boxed {@link Long} values.
     */
    Stream<Long> boxed();

    /**
     * Limits how many elements will be evaluated in the {@link LongStream}. If the current {@link LongStream}
     * contains more elements than the supplied limit then all remaining elements will be ignored.
     *
     * @param maxSize Maximum number of elements which should be evaluated in the current {@link Stream}.
     * @return Returns a new {@link Stream} which contains all elements up to the supplied limit.
     */
    LongStream limit(long maxSize);

    /**
     * Skips the supplied number of elements at the start of the {@link LongStream}. If the number of
     * elements in the {@link LongStream} is lower than the skipped count an empty {@link LongStream} will
     * be returned.
     *
     * @param count How many elements should be skipped
     * @return Returns a new {@link LongStream} which does not contain any of the skipped elements.
     */
    LongStream skip(long count);

    /**
     * Reduces the elements in the {@link LongStream} according to the supplied {@link LongBinaryOperator}.
     *
     * @param identity The identity value used for the reduction.
     * @param op       The {@link LongBinaryOperator} used for the reduction.
     * @return Returns the result of the reduction operation as a {@link long} value.
     */
    long reduce(long identity, LongBinaryOperator op);

    /**
     * Reduces the elements in the {@link LongStream} according to the supplied {@link LongBinaryOperator}.
     *
     * @param op The {@link LongBinaryOperator} used for the reduction.
     * @return Returns the result of the reduction operation as an {@link OptionalLong} value.
     */
    OptionalLong reduce(LongBinaryOperator op);

    /**
     * Collects the elements in the {@link LongStream}
     *
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator);

    /**
     *
     *
     * @param cls
     * @param <E>
     * @return
     */
    <E extends Throwable> LongExceptional<E> exception(Class<E> cls);

    /**
     *
     *
     * @return
     */
    long sum();

    /**
     * @return
     */
    OptionalLong min();

    /**
     * @return
     */
    OptionalLong max();

    /**
     * @return
     */
    long count();

    /**
     * Sorts the elements in the {@link LongStream} in ascending order.
     *
     * @return Returns a new {@link LongStream} which contains the elements in ascending order.
     */
    LongStream sort();

    /**
     * @return
     */
    OptionalDouble average();

    /**
     * @return
     */
    OptionalLong findFirst();

    /**
     * @param predicate
     * @return
     */
    boolean anyMatch(LongPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean allMatch(LongPredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean noneMatch(LongPredicate predicate);

    /**
     * @return
     */
    long[] toArray();

    /**
     * @return
     */
    @Override
    LongIterator iterator();

    /**
     * @param action
     */
    void forEach(LongConsumer action);
}
