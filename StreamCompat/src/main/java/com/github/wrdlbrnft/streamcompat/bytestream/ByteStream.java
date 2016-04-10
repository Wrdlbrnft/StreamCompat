package com.github.wrdlbrnft.streamcompat.bytestream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.ByteBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ByteConsumer;
import com.github.wrdlbrnft.streamcompat.function.ByteFunction;
import com.github.wrdlbrnft.streamcompat.function.BytePredicate;
import com.github.wrdlbrnft.streamcompat.function.ByteToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ByteToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ByteToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ByteToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ByteToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.ByteUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjByteConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalByte;
import com.github.wrdlbrnft.streamcompat.stream.Stream;

/**
 * The primitive version of a {@link Stream} which contains a sequence of {@code byte} values.
 */
public interface ByteStream extends Iterable<Byte> {

    /**
     * @param predicate
     * @return
     */
    ByteStream filter(BytePredicate predicate);

    /**
     * @param mapper
     * @return
     */
    ByteStream map(ByteUnaryOperator mapper);

    /**
     * @param mapper
     * @return
     */
    ByteStream flatMap(ByteFunction<? extends ByteStream> mapper);

    /**
     * @param mapper
     * @param <U>
     * @return
     */
    <U> Stream<U> mapToObj(ByteFunction<? extends U> mapper);

    /**
     * @param mapper
     * @return
     */
    LongStream mapToLong(ByteToLongFunction mapper);

    /**
     * @param mapper
     * @return
     */
    IntStream mapToInt(ByteToIntFunction mapper);

    /**
     * @param mapper
     * @return
     */
    DoubleStream mapToDouble(ByteToDoubleFunction mapper);

    /**
     * @param mapper
     * @return
     */
    FloatStream mapToFloat(ByteToFloatFunction mapper);

    /**
     * @param mapper
     * @return
     */
    CharacterStream mapToChar(ByteToCharFunction mapper);

    /**
     * @return
     */
    Stream<Byte> boxed();

    /**
     * @param limit
     * @return
     */
    ByteStream limit(long limit);

    /**
     * @param identity
     * @param op
     * @return
     */
    byte reduce(byte identity, ByteBinaryOperator op);

    /**
     * @param op
     * @return
     */
    OptionalByte reduce(ByteBinaryOperator op);

    /**
     * @param supplier
     * @param accumulator
     * @param <R>
     * @return
     */
    <R> R collect(Supplier<R> supplier, ObjByteConsumer<R> accumulator);

    /**
     * @return
     */
    OptionalByte min();

    /**
     * @return
     */
    OptionalByte max();

    /**
     * @return
     */
    long count();

    /**
     * @return
     */
    OptionalByte findFirst();

    /**
     * @param predicate
     * @return
     */
    boolean anyMatch(BytePredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean allMatch(BytePredicate predicate);

    /**
     * @param predicate
     * @return
     */
    boolean noneMatch(BytePredicate predicate);

    /**
     * @return
     */
    byte[] toArray();

    /**
     * @return
     */
    @Override
    ByteIterator iterator();

    /**
     * @param action
     */
    void forEach(ByteConsumer action);
}
