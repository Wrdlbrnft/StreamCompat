package com.github.wrdlbrnft.streamcompat.bytestream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
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
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalByte;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.Utils;

/**
 * Created by kapeller on 21/03/16.
 */
class ByteStreamImpl implements ByteStream {

    private static final int DEFAULT_ARRAY_SIZE = 16;

    private final ByteIterator mIterator;

    ByteStreamImpl(ByteIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public ByteStream filter(BytePredicate predicate) {
        Utils.requireNonNull(predicate);
        final DummyIterator iterator = new DummyIterator();
        return new ByteStreamImpl(new ByteChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final byte c = mIterator.nextByte();
                        if (predicate.test(c)) {
                            return iterator.newValue(c);
                        }
                    }
                    return iterator;
                },
                ByteIterator::hasNext,
                ByteIterator::nextByte
        ));
    }

    @Override
    public ByteStream map(ByteUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new ByteStreamImpl(new ByteChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsByte(iterator.nextByte())
        ));
    }

    @Override
    public ByteStream flatMap(ByteFunction<? extends ByteStream> mapper) {
        Utils.requireNonNull(mapper);
        final ByteIterator[] array = new ByteIterator[1];
        return new ByteStreamImpl(new ByteChildIterator<>(
                () -> {
                    if (array[0] == null || !array[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return array[0] = ByteStreamCompat.EMPTY_ITERATOR;
                        }
                        array[0] = mapper.apply(mIterator.nextByte()).iterator();
                    }

                    return array[0];
                },
                ByteIterator::hasNext,
                ByteIterator::nextByte
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(ByteFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextByte())
        ));
    }

    @Override
    public LongStream mapToLong(ByteToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsLong(mIterator.nextByte())
        ));
    }

    @Override
    public IntStream mapToInt(ByteToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextByte())
        ));
    }

    @Override
    public DoubleStream mapToDouble(ByteToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsDouble(mIterator.nextByte())
        ));
    }

    @Override
    public FloatStream mapToFloat(ByteToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextByte())
        ));
    }

    @Override
    public CharacterStream mapToChar(ByteToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                ByteIterator::hasNext,
                iterator -> mapper.applyAsChar(mIterator.nextByte())
        ));
    }

    @Override
    public ByteIterator iterator() {
        return mIterator;
    }

    @Override
    public void forEach(ByteConsumer action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final byte value = mIterator.nextByte();
            action.accept(value);
        }
    }

    @Override
    public Stream<Byte> boxed() {
        return mapToObj(Byte::valueOf);
    }

    @Override
    public ByteStream limit(long limit) {
        final long[] array = {0, limit};
        return new ByteStreamImpl(new ByteChildIterator<>(
                () -> mIterator,
                i -> array[0] < array[1] && i.hasNext(),
                i -> {
                    array[0]++;
                    return i.nextByte();
                }
        ));
    }

    @Override
    public byte reduce(byte identity, ByteBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        byte current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsByte(current, mIterator.nextByte());
        }
        return current;
    }

    @Override
    public OptionalByte reduce(ByteBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalByte.empty();
        }

        byte current = mIterator.nextByte();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsByte(current, mIterator.nextByte());
        }
        return OptionalByte.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjByteConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextByte());
        }
        return sink;
    }

    public byte sum() {
        return reduce((byte) 0, (a, b) -> (byte) (a + b));
    }

    @Override
    public OptionalByte min() {
        return reduce((a, b) -> a < b ? a : b);
    }

    @Override
    public OptionalByte max() {
        return reduce((a, b) -> a < b ? b : a);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    public OptionalDouble average() {
        final long[] avg = collect(() -> new long[2],
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
    public OptionalByte findFirst() {
        return mIterator.hasNext()
                ? OptionalByte.of(mIterator.nextByte())
                : OptionalByte.empty();
    }

    @Override
    public boolean anyMatch(BytePredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextByte())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(BytePredicate predicate) {
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextByte())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(BytePredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextByte())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public byte[] toArray() {
        byte[] tmp = new byte[DEFAULT_ARRAY_SIZE];
        int index = 0;
        while (mIterator.hasNext()) {
            final byte c = mIterator.nextByte();

            if (index >= tmp.length) {
                final byte[] newArray = new byte[tmp.length * 2];
                System.arraycopy(tmp, 0, newArray, 0, tmp.length);
                tmp = newArray;
            }

            tmp[index++] = c;
        }

        final byte[] result = new byte[index];
        System.arraycopy(tmp, 0, result, 0, index);
        return result;
    }

    private static class DummyIterator extends BaseIterator<Byte> implements ByteIterator {

        private byte mValue;
        private boolean mHasNext;

        public DummyIterator(byte value) {
            this(value, true);
        }

        public DummyIterator() {
            this((byte) 0, false);
        }

        private DummyIterator(byte value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(byte value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public byte nextByte() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Byte next() {
            return nextByte();
        }
    }
}
