package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.DoubleBinaryOperator;
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
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleStreamImpl implements DoubleStream {

    private final DoubleIterator mIterator;

    DoubleStreamImpl(DoubleIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        Utils.requireNonNull(predicate);
        final DoubleIterator iterator = new DoublePredicateIterator(mIterator, predicate);
        return new DoubleStreamImpl(iterator);
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new DoubleMappingIterator(mIterator, mapper);
        return new DoubleStreamImpl(iterator);
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new DoubleFlatMappingIterator(mIterator, mapper);
        return new DoubleStreamImpl(iterator);
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<U> iterator = new DoubleToObjectMappingIterator<>(mIterator, mapper);
        return StreamCompat.of(iterator);
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new DoubleToLongMappingIterator(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public FloatStream mapToFloat(DoubleToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new DoubleToFloatMappingIterator(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new DoubleToIntMappingIterator(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public CharacterStream mapToChar(DoubleToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new DoubleToCharMappingIterator(mIterator, mapper);
        return CharacterStreamCompat.of(iterator);
    }

    @Override
    public DoubleIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Double> boxed() {
        return StreamCompat.of(mIterator);
    }

    @Override
    public DoubleStream limit(long limit) {
        final DoubleIterator iterator = new DoubleLimitIterator(mIterator, limit);
        return new DoubleStreamImpl(iterator);
    }

    @Override
    public double reduce(double identity, DoubleBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        double current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsDouble(current, mIterator.nextDouble());
        }
        return current;
    }

    @Override
    public OptionalDouble reduce(DoubleBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalDouble.empty();
        }

        double current = mIterator.nextDouble();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsDouble(current, mIterator.nextDouble());
        }
        return OptionalDouble.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjDoubleConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextDouble());
        }
        return sink;
    }

    @Override
    public double sum() {
        double[] summation = collect(() -> new double[3],
                (ll, d) -> {
                    KahanSummation.sumWithCompensation(ll, d);
                    ll[2] += d;
                }
        );

        return KahanSummation.computeFinalSum(summation);
    }

    @Override
    public OptionalDouble min() {
        return reduce(Math::min);
    }

    @Override
    public OptionalDouble max() {
        return reduce(Math::max);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
    public OptionalDouble average() {
        double[] avg = collect(() -> new double[4],
                (ll, d) -> {
                    ll[2]++;
                    KahanSummation.sumWithCompensation(ll, d);
                    ll[3] += d;
                }
        );
        return avg[2] > 0
                ? OptionalDouble.of(KahanSummation.computeFinalSum(avg) / avg[2])
                : OptionalDouble.empty();
    }

    @Override
    public OptionalDouble findFirst() {
        return mIterator.hasNext()
                ? OptionalDouble.of(mIterator.nextDouble())
                : OptionalDouble.empty();
    }

    @Override
    public boolean anyMatch(DoublePredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextDouble())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(DoublePredicate predicate) {
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextDouble())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(DoublePredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextDouble())) {
                return false;
            }
        }
        return true;
    }
}
