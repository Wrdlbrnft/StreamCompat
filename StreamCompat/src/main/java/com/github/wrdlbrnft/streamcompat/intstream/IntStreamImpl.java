package com.github.wrdlbrnft.streamcompat.intstream;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.IntBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.IntConsumer;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.function.IntToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.IntToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.IntUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjIntConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.array.IntArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalInt;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Arrays;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
class IntStreamImpl implements IntStream {

    private static final int DEFAULT_ARRAY_SIZE = 16;

    private final IntIteratorWrapper mIteratorWrapper = new IntIteratorWrapperImpl();
    private final IntIterator mIterator;

    IntStreamImpl(IntIterator iterator) {
        mIterator = mIteratorWrapper.apply(iterator);
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        Utils.requireNonNull(predicate);
        final DummyIterator iterator = new DummyIterator();
        return new IntStreamImpl(new IntChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final int value = mIterator.nextInt();
                        if (predicate.test(value)) {
                            return iterator.newValue(value);
                        }
                    }
                    return iterator;
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new IntStreamImpl(new IntChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextInt())
        ));
    }

    @Override
    public IntStream flatMap(IntFunction<? extends IntStream> mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator[] buffer = new IntIterator[1];
        return new IntStreamImpl(new IntChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return IntStreamCompat.EMPTY_ITERATOR;
                        }
                        buffer[0] = mapper.apply(mIterator.nextInt()).iterator();
                    }
                    return buffer[0];
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(IntFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextInt())
        ));
    }

    @Override
    public LongStream mapToLong(IntToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsLong(mIterator.nextInt())
        ));
    }

    @Override
    public DoubleStream mapToDouble(IntToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsDouble(mIterator.nextInt())
        ));
    }

    @Override
    public FloatStream mapToFloat(IntToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextInt())
        ));
    }

    @Override
    public CharacterStream mapToChar(IntToCharFunction mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsChar(mIterator.nextInt())
        ));
    }

    @Override
    public ByteStream mapToByte(IntToByteFunction mapper) {
        Utils.requireNonNull(mapper);
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> mIterator,
                IntIterator::hasNext,
                iterator -> mapper.applyAsByte(mIterator.nextInt())
        ));
    }

    @NonNull
    @Override
    public IntIterator iterator() {
        return mIterator;
    }

    @Override
    public void forEach(IntConsumer action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final int i = mIterator.nextInt();
            action.accept(i);
        }
    }

    @Override
    public Stream<Integer> boxed() {
        return mapToObj(Integer::valueOf);
    }

    @Override
    public IntStream limit(long limit) {
        final long[] buffer = {0, limit};
        return new IntStreamImpl(new IntChildIterator<>(
                () -> mIterator,
                iterator -> buffer[0] < buffer[1] && mIterator.hasNext(),
                iterator -> {
                    buffer[0]++;
                    return iterator.nextInt();
                }
        ));
    }

    @Override
    public IntStream skip(long count) {
        final long[] buffer = {0, count};
        return new IntStreamImpl(new IntChildIterator<>(
                () -> {
                    while (mIterator.hasNext() && buffer[0] < buffer[1]) {
                        mIterator.nextInt();
                        buffer[0]++;
                    }
                    return mIterator;
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
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
    public <E extends Throwable> IntExceptional<E> exception(Class<E> cls) {
        return new IntExceptionalImpl<>(cls);
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
    public IntStream sort() {
        return new IntStreamImpl(new IntChildIterator<>(
                () -> {
                    final int[] array = toArray();
                    Arrays.sort(array);
                    return new IntArrayIterator(array);
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
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

    @Override
    public int[] toArray() {
        int[] tmp = new int[DEFAULT_ARRAY_SIZE];
        int index = 0;
        while (mIterator.hasNext()) {
            final int c = mIterator.nextInt();

            if (index >= tmp.length) {
                final int[] newArray = new int[tmp.length * 2];
                System.arraycopy(tmp, 0, newArray, 0, tmp.length);
                tmp = newArray;
            }

            tmp[index++] = c;
        }

        final int[] result = new int[index];
        System.arraycopy(tmp, 0, result, 0, index);
        return result;
    }

    private static class DummyIterator extends BaseIterator<Integer> implements IntIterator {

        private int mValue;
        private boolean mHasNext;

        public DummyIterator(int value) {
            this(value, true);
        }

        public DummyIterator() {
            this(0, false);
        }

        private DummyIterator(int value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(int value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public int nextInt() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Integer next() {
            return nextInt();
        }
    }

    private class IntExceptionalImpl<E extends Throwable> implements IntExceptional<E> {

        private final Class<E> mExceptionClass;

        public IntExceptionalImpl(Class<E> exceptionClass) {
            mExceptionClass = exceptionClass;
        }

        @Override
        public IntStream mapException(ToIntFunction<E> mapper) {
            mIteratorWrapper.mapException(mExceptionClass, mapper);
            return IntStreamImpl.this;
        }

        @Override
        public IntStream consume(Consumer<E> consumer) {
            mIteratorWrapper.consumeException(mExceptionClass, consumer);
            return IntStreamImpl.this;
        }

        @Override
        public <I extends RuntimeException> IntStream rethrow(Function<E, I> mapper) {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
                throw mapper.apply(e);
            });
            return IntStreamImpl.this;
        }

        @Override
        public IntStream ignore() {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
            });
            return IntStreamImpl.this;
        }
    }
}
