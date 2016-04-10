package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.FloatBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.FloatConsumer;
import com.github.wrdlbrnft.streamcompat.function.FloatFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatPredicate;
import com.github.wrdlbrnft.streamcompat.function.FloatToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.FloatUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjFloatConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.KahanSummation;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalFloat;
import com.github.wrdlbrnft.streamcompat.util.Utils;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatStreamImpl implements FloatStream {

    private static final int DEFAULT_ARRAY_SIZE = 16;
    private final FloatIterator mIterator;

    FloatStreamImpl(FloatIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public FloatStream filter(FloatPredicate predicate) {
        Utils.requireNonNull(predicate);
        final DummyIterator iterator = new DummyIterator();
        return new FloatStreamImpl(new FloatChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final float value = mIterator.nextFloat();
                        if (predicate.test(value)) {
                            return iterator.newValue(value);
                        }
                    }
                    return iterator;
                },
                FloatIterator::hasNext,
                FloatIterator::nextFloat
        ));
    }

    @Override
    public FloatStream map(FloatUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new FloatStreamImpl(new FloatChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextFloat())
        ));
    }

    @Override
    public FloatStream flatMap(FloatFunction<? extends FloatStream> mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator[] buffer = new FloatIterator[1];
        return new FloatStreamImpl(new FloatChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return FloatStreamCompat.EMPTY_ITERATOR;
                        }
                        buffer[0] = mapper.apply(mIterator.nextFloat()).iterator();
                    }
                    return buffer[0];
                },
                FloatIterator::hasNext,
                FloatIterator::nextFloat
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(FloatFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextFloat())
        ));
    }

    @Override
    public LongStream mapToLong(FloatToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsLong(mIterator.nextFloat())
        ));
    }

    @Override
    public IntStream mapToInt(FloatToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextFloat())
        ));
    }

    @Override
    public CharacterStream mapToChar(FloatToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsChar(mIterator.nextFloat())
        ));
    }

    @Override
    public DoubleStream mapToDouble(FloatToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsDouble(mIterator.nextFloat())
        ));
    }

    @Override
    public ByteStream mapToByte(FloatToByteFunction mapper) {
        Utils.requireNonNull(mapper);
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> mIterator,
                FloatIterator::hasNext,
                iterator -> mapper.applyAsByte(mIterator.nextFloat())
        ));
    }

    @Override
    public FloatIterator iterator() {
        return mIterator;
    }

    @Override
    public void forEach(FloatConsumer action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final float value = mIterator.nextFloat();
            action.accept(value);
        }
    }

    @Override
    public Stream<Float> boxed() {
        return mapToObj(Float::valueOf);
    }

    @Override
    public FloatStream limit(long limit) {
        final long[] buffer = {0, limit};
        return new FloatStreamImpl(new FloatChildIterator<>(
                () -> mIterator,
                iterator -> buffer[0] < buffer[1] && mIterator.hasNext(),
                iterator -> {
                    buffer[0]++;
                    return mIterator.nextFloat();
                }
        ));
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

    @Override
    public float[] toArray() {
        float[] tmp = new float[DEFAULT_ARRAY_SIZE];
        int index = 0;
        while (mIterator.hasNext()) {
            final float c = mIterator.nextFloat();

            if (index >= tmp.length) {
                final float[] newArray = new float[tmp.length * 2];
                System.arraycopy(tmp, 0, newArray, 0, tmp.length);
                tmp = newArray;
            }

            tmp[index++] = c;
        }

        final float[] result = new float[index];
        System.arraycopy(tmp, 0, result, 0, index);
        return result;
    }

    private static class DummyIterator extends BaseIterator<Float> implements FloatIterator {

        private float mValue;
        private boolean mHasNext;

        public DummyIterator(float value) {
            this(value, true);
        }

        public DummyIterator() {
            this(0.0f, false);
        }

        private DummyIterator(float value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(float value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public float nextFloat() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Float next() {
            return nextFloat();
        }
    }
}
