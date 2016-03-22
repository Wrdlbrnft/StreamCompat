package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.FloatBinaryOperator;
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
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.KahanSummation;
import com.github.wrdlbrnft.streamcompat.util.OptionalFloat;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatStreamImpl implements FloatStream {

    private final FloatIterator mIterator;

    FloatStreamImpl(FloatIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public FloatStream filter(FloatPredicate predicate) {
        Utils.requireNonNull(predicate);
        final FloatIterator iterator = new FloatPredicateIterator(mIterator, predicate);
        return new FloatStreamImpl(iterator);
    }

    @Override
    public FloatStream map(FloatUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new FloatMappingIterator(mIterator, mapper);
        return new FloatStreamImpl(iterator);
    }

    @Override
    public FloatStream flatMap(FloatFunction<? extends FloatStream> mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new FloatFlatMappingIterator(mIterator, mapper);
        return new FloatStreamImpl(iterator);
    }

    @Override
    public <U> Stream<U> mapToObj(FloatFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<U> iterator = new FloatToObjectMappingIterator<>(mIterator, mapper);
        return StreamCompat.of(iterator);
    }

    @Override
    public LongStream mapToLong(FloatToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new FloatToLongMappingIterator(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public IntStream mapToInt(FloatToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new FloatToIntMappingIterator(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public CharacterStream mapToChar(FloatToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new FloatToCharMappingIterator(mIterator, mapper);
        return CharacterStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream mapToDouble(FloatToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new FloatToDoubleMappingIterator(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public FloatIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Float> boxed() {
        return StreamCompat.of(mIterator);
    }

    @Override
    public FloatStream limit(long limit) {
        final FloatIterator iterator = new FloatLimitIterator(mIterator, limit);
        return new FloatStreamImpl(iterator);
    }

    @Override
    public float reduce(float identity, FloatBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        float current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsFloat(current, mIterator.nextFloat());
        }
        return current;
    }

    @Override
    public OptionalFloat reduce(FloatBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalFloat.empty();
        }

        float current = mIterator.nextFloat();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsFloat(current, mIterator.nextFloat());
        }
        return OptionalFloat.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjFloatConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextFloat());
        }
        return sink;
    }

    @Override
    public float sum() {
        float[] summation = collect(() -> new float[3],
                (ll, d) -> {
                    KahanSummation.sumWithCompensation(ll, d);
                    ll[2] += d;
                }
        );

        return KahanSummation.computeFinalSum(summation);
    }

    @Override
    public OptionalFloat min() {
        return reduce(Math::min);
    }

    @Override
    public OptionalFloat max() {
        return reduce(Math::max);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
    public OptionalFloat average() {
        float[] avg = collect(() -> new float[4],
                (ll, d) -> {
                    ll[2]++;
                    KahanSummation.sumWithCompensation(ll, d);
                    ll[3] += d;
                }
        );
        return avg[2] > 0
                ? OptionalFloat.of(KahanSummation.computeFinalSum(avg) / avg[2])
                : OptionalFloat.empty();
    }

    @Override
    public OptionalFloat findFirst() {
        return mIterator.hasNext()
                ? OptionalFloat.of(mIterator.nextFloat())
                : OptionalFloat.empty();
    }

    @Override
    public boolean anyMatch(FloatPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextFloat())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(FloatPredicate predicate) {
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextFloat())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(FloatPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextFloat())) {
                return false;
            }
        }
        return true;
    }
}
