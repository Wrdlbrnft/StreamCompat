package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.IntBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.function.IntToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.IntUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjIntConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.OptionalInt;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntStreamImpl implements IntStream {

    private final IntIterator mIterator;

    IntStreamImpl(IntIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        Utils.requireNonNull(predicate);
        final IntIterator iterator = new IntPredicateIterator(mIterator, predicate);
        return new IntStreamImpl(iterator);
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new IntMappingIterator(mIterator, mapper);
        return new IntStreamImpl(iterator);
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new IntFlatMappingIterator(mIterator, mapper);
        return new IntStreamImpl(iterator);
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<U> iterator = new IntToObjectMappingIterator<>(mIterator, mapper);
        return StreamCompat.of(iterator);
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new IntToLongMappingIterator(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new IntToDoubleMappingIterator(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public FloatStream mapToFloat(IntToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new IntToFloatMappingIterator(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public CharacterStream mapToChar(IntToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new IntToCharMappingIterator(mIterator, mapper);
        return CharacterStreamCompat.of(iterator);
    }

    @Override
    public IntIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Integer> boxed() {
        return StreamCompat.of(mIterator);
    }

    @Override
    public IntStream limit(long limit) {
        final IntIterator iterator = new IntLimitIterator(mIterator, limit);
        return new IntStreamImpl(iterator);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        int current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsInt(current, mIterator.nextInt());
        }
        return current;
    }

    @Override
    public OptionalInt reduce(IntBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalInt.empty();
        }

        int current = mIterator.nextInt();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsInt(current, mIterator.nextInt());
        }
        return OptionalInt.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjIntConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextInt());
        }
        return sink;
    }

    @Override
    public int sum() {
        return reduce(0, (a, b) -> a + b);
    }

    @Override
    public OptionalInt min() {
        return reduce(Math::min);
    }

    @Override
    public OptionalInt max() {
        return reduce(Math::max);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
    public OptionalDouble average() {
        long[] avg = collect(() -> new long[2],
                (ll, i) -> {
                    ll[0]++;
                    ll[1] += i;
                }
        );
        return avg[0] > 0
                ? OptionalDouble.of((double) avg[1] / avg[0])
                : OptionalDouble.empty();
    }

    @Override
    public OptionalInt findFirst() {
        return mIterator.hasNext()
                ? OptionalInt.of(mIterator.nextInt())
                : OptionalInt.empty();
    }

    @Override
    public boolean anyMatch(IntPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextInt())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(IntPredicate predicate) {
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextInt())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(IntPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextInt())) {
                return false;
            }
        }
        return true;
    }
}
