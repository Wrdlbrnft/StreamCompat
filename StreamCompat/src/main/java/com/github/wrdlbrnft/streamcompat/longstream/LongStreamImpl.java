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
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.OptionalLong;
import com.github.wrdlbrnft.streamcompat.util.Utils;

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
        final DummyIterator iterator = new DummyIterator();
        return new LongStreamImpl(new LongChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final long value = mIterator.nextLong();
                        if (predicate.test(value)) {
                            return iterator.newValue(value);
                        }
                    }
                    return iterator;
                },
                LongIterator::hasNext,
                LongIterator::nextLong
        ));
    }

    @Override
    public LongStream map(LongUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new LongStreamImpl(new LongChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.applyAsLong(iterator.nextLong())
        ));
    }

    @Override
    public LongStream flatMap(LongFunction<? extends LongStream> mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator[] buffer = new LongIterator[1];
        return new LongStreamImpl(new LongChildIterator<>(
                () -> {
                    if(buffer[0] == null || !buffer[0].hasNext()) {
                        if(!mIterator.hasNext()) {
                            return LongStreamCompat.EMPTY_ITERATOR;
                        }
                        buffer[0] = mapper.apply(mIterator.nextLong()).iterator();
                    }
                    return buffer[0];
                },
                LongIterator::hasNext,
                LongIterator::nextLong
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(LongFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextLong())
        ));
    }

    @Override
    public IntStream mapToInt(LongToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextLong())
        ));
    }

    @Override
    public DoubleStream mapToDouble(LongToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.applyAsDouble(mIterator.nextLong())
        ));
    }

    @Override
    public FloatStream mapToFloat(LongToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextLong())
        ));
    }

    @Override
    public CharacterStream mapToChar(LongToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                LongIterator::hasNext,
                iterator -> mapper.applyAsChar(mIterator.nextLong())
        ));
    }

    @Override
    public LongIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Long> boxed() {
        return mapToObj(Long::valueOf);
    }

    @Override
    public LongStream limit(long limit) {
        final long[] buffer = {0, limit};
        return new LongStreamImpl(new LongChildIterator<>(
                () -> mIterator,
                iterator -> buffer[0] < buffer[1] && mIterator.hasNext(),
                (iterator) -> {
                    buffer[0]++;
                    return iterator.nextLong();
                }
        ));
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

    private static class DummyIterator extends BaseIterator<Long> implements LongIterator {

        private long mValue;
        private boolean mHasNext;

        public DummyIterator(long value) {
            this(value, true);
        }

        public DummyIterator() {
            this(0, false);
        }

        private DummyIterator(long value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(long value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public long nextLong() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Long next() {
            return nextLong();
        }
    }
}
