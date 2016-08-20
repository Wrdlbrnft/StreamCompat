package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.DoubleBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.DoubleConsumer;
import com.github.wrdlbrnft.streamcompat.function.DoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.DoublePredicate;
import com.github.wrdlbrnft.streamcompat.function.DoubleToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.DoubleUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjDoubleConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.array.DoubleArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.KahanSummation;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Arrays;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleStreamImpl implements DoubleStream {

    private static final int DEFAULT_ARRAY_SIZE = 16;
    private final DoubleIterator mIterator;

    DoubleStreamImpl(DoubleIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public DoubleStream filter(DoublePredicate predicate) {
        Utils.requireNonNull(predicate);
        final DummyIterator iterator = new DummyIterator();
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final double value = mIterator.nextDouble();
                        if (predicate.test(value)) {
                            return iterator.newValue(value);
                        }
                    }
                    return iterator;
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
    }

    @Override
    public DoubleStream map(DoubleUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsDouble(iterator.nextDouble())
        ));
    }

    @Override
    public DoubleStream flatMap(DoubleFunction<? extends DoubleStream> mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator[] buffer = new DoubleIterator[1];
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return DoubleStreamCompat.EMPTY_ITERATOR;
                        }
                        buffer[0] = mapper.apply(mIterator.nextDouble()).iterator();
                    }
                    return buffer[0];
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(DoubleFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextDouble())
        ));
    }

    @Override
    public LongStream mapToLong(DoubleToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsLong(mIterator.nextDouble())
        ));
    }

    @Override
    public FloatStream mapToFloat(DoubleToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextDouble())
        ));
    }

    @Override
    public IntStream mapToInt(DoubleToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextDouble())
        ));
    }

    @Override
    public CharacterStream mapToChar(DoubleToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsChar(mIterator.nextDouble())
        ));
    }

    @Override
    public ByteStream mapToByte(DoubleToByteFunction mapper) {
        Utils.requireNonNull(mapper);
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> mIterator,
                DoubleIterator::hasNext,
                iterator -> mapper.applyAsByte(mIterator.nextDouble())
        ));
    }

    @Override
    public DoubleIterator iterator() {
        return mIterator;
    }

    @Override
    public void forEach(DoubleConsumer action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final double value = mIterator.nextDouble();
            action.accept(value);
        }
    }

    @Override
    public Stream<Double> boxed() {
        return mapToObj(Double::valueOf);
    }

    @Override
    public DoubleStream limit(long limit) {
        final long[] buffer = {0, limit};
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> mIterator,
                iterator -> buffer[0] < buffer[1] && mIterator.hasNext(),
                iterator -> {
                    buffer[0]++;
                    return mIterator.nextDouble();
                }
        ));
    }

    @Override
    public DoubleStream skip(long count) {
        final long[] buffer = {0, count};
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> {
                    while (mIterator.hasNext() && buffer[0] < buffer[1]) {
                        mIterator.nextDouble();
                        buffer[0]++;
                    }
                    return mIterator;
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
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
    public DoubleStream sort() {
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> {
                    final double[] array = toArray();
                    Arrays.sort(array);
                    return new DoubleArrayIterator(array);
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
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

    @Override
    public double[] toArray() {
        double[] tmp = new double[DEFAULT_ARRAY_SIZE];
        int index = 0;
        while (mIterator.hasNext()) {
            final double c = mIterator.nextDouble();

            if (index >= tmp.length) {
                final double[] newArray = new double[tmp.length * 2];
                System.arraycopy(tmp, 0, newArray, 0, tmp.length);
                tmp = newArray;
            }

            tmp[index++] = c;
        }

        final double[] result = new double[index];
        System.arraycopy(tmp, 0, result, 0, index);
        return result;
    }

    private static class DummyIterator extends BaseIterator<Double> implements DoubleIterator {

        private double mValue;
        private boolean mHasNext;

        public DummyIterator(double value) {
            this(value, true);
        }

        public DummyIterator() {
            this(0.0f, false);
        }

        private DummyIterator(double value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(double value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public double nextDouble() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Double next() {
            return nextDouble();
        }
    }
}
