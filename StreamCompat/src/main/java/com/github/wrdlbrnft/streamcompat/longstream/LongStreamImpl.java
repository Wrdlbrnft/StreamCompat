package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
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
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.OptionalLong;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongStreamImpl implements LongStream {

    private final LongIterator mIterator;

    LongStreamImpl(LongIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public LongStream filter(LongPredicate predicate) {
        Utils.requireNonNull(predicate);
        final LongIterator iterator = new LongPredicateIterator(mIterator, predicate);
        return new LongStreamImpl(iterator);
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new LongMappingIterator(mIterator, mapper);
        return new LongStreamImpl(iterator);
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new LongFlatMappingIterator(mIterator, mapper);
        return new LongStreamImpl(iterator);
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<U> iterator = new LongToObjectMappingIterator<>(mIterator, mapper);
        return StreamCompat.of(iterator);
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new LongToIntMappingIterator(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new LongToDoubleMappingIterator(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public FloatStream mapToFloat(LongToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new LongToFloatMappingIterator(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public CharacterStream mapToChar(LongToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new LongToCharMappingIterator(mIterator, mapper);
        return CharacterStreamCompat.of(iterator);
    }

    @Override
    public LongIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Long> boxed() {
        return StreamCompat.of(mIterator);
    }

    @Override
    public LongStream limit(long limit) {
        final LongIterator iterator = new LongLimitIterator(mIterator, limit);
        return new LongStreamImpl(iterator);
    }

    @Override
    public long reduce(long identity, LongBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);

        long current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsInt(current, mIterator.nextLong());
        }
        return current;
    }

    @Override
    public OptionalLong reduce(LongBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);

        if (!mIterator.hasNext()) {
            return OptionalLong.empty();
        }

        long current = mIterator.nextLong();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsInt(current, mIterator.nextLong());
        }
        return OptionalLong.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjLongConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextLong());
        }
        return sink;
    }

    @Override
    public long sum() {
        return reduce(0, (a, b) -> a + b);
    }

    @Override
    public OptionalLong min() {
        return reduce(Math::min);
    }

    @Override
    public OptionalLong max() {
        return reduce(Math::max);
    }

    @Override
    public long count() {
        return map(i -> 1L).sum();
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
    public OptionalLong findFirst() {
        if (mIterator.hasNext()) {
            return OptionalLong.of(mIterator.nextLong());
        }

        return OptionalLong.empty();
    }

    @Override
    public boolean anyMatch(LongPredicate predicate) {
        Utils.requireNonNull(predicate);
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextLong())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean allMatch(LongPredicate predicate) {
        Utils.requireNonNull(predicate);
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextLong())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean noneMatch(LongPredicate predicate) {
        Utils.requireNonNull(predicate);
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextLong())) {
                return false;
            }
        }

        return true;
    }
}
