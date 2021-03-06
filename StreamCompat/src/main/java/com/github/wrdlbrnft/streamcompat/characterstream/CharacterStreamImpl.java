package com.github.wrdlbrnft.streamcompat.characterstream;

import android.support.annotation.NonNull;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.CharBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.CharConsumer;
import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.function.CharPredicate;
import com.github.wrdlbrnft.streamcompat.function.CharToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.CharUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.ObjCharConsumer;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.array.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.IntChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.LongChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalCharacter;
import com.github.wrdlbrnft.streamcompat.optionals.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Arrays;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
class CharacterStreamImpl implements CharacterStream {

    private static final int DEFAULT_ARRAY_SIZE = 16;

    private final CharacterIteratorWrapper mIteratorWrapper = new CharacterIteratorWrapperImpl();
    private final CharIterator mIterator;

    CharacterStreamImpl(CharIterator iterator) {
        mIterator = mIteratorWrapper.apply(iterator);
    }

    @Override
    public CharacterStream filter(CharPredicate predicate) {
        Utils.requireNonNull(predicate);
        final DummyIterator iterator = new DummyIterator();
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final char c = mIterator.nextChar();
                        if (predicate.test(c)) {
                            return iterator.newValue(c);
                        }
                    }
                    return iterator;
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
    }

    @Override
    public CharacterStream map(CharUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsChar(iterator.nextChar())
        ));
    }

    @Override
    public CharacterStream flatMap(CharFunction<? extends CharacterStream> mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator[] array = new CharIterator[1];
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> {
                    if (array[0] == null || !array[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return array[0] = CharacterStreamCompat.EMPTY_ITERATOR;
                        }
                        array[0] = mapper.apply(mIterator.nextChar()).iterator();
                    }

                    return array[0];
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
    }

    @Override
    public <U> Stream<U> mapToObj(CharFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        return StreamCompat.of(new ChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.apply(mIterator.nextChar())
        ));
    }

    @Override
    public LongStream mapToLong(CharToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsLong(mIterator.nextChar())
        ));
    }

    @Override
    public IntStream mapToInt(CharToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsInt(mIterator.nextChar())
        ));
    }

    @Override
    public DoubleStream mapToDouble(CharToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsDouble(mIterator.nextChar())
        ));
    }

    @Override
    public FloatStream mapToFloat(CharToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsFloat(mIterator.nextChar())
        ));
    }

    @Override
    public ByteStream mapToByte(CharToByteFunction mapper) {
        Utils.requireNonNull(mapper);
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> mIterator,
                CharIterator::hasNext,
                iterator -> mapper.applyAsByte(mIterator.nextChar())
        ));
    }

    @NonNull
    @Override
    public CharIterator iterator() {
        return mIterator;
    }

    @Override
    public void forEach(CharConsumer action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final char value = mIterator.nextChar();
            action.accept(value);
        }
    }

    @Override
    public Stream<Character> boxed() {
        return mapToObj(Character::valueOf);
    }

    @Override
    public CharacterStream limit(long limit) {
        final long[] array = {0, limit};
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> mIterator,
                i -> array[0] < array[1] && i.hasNext(),
                i -> {
                    array[0]++;
                    return i.nextChar();
                }
        ));
    }

    @Override
    public CharacterStream skip(long count) {
        final long[] buffer = {0, count};
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> {
                    while (mIterator.hasNext() && buffer[0] < buffer[1]) {
                        mIterator.nextChar();
                        buffer[0]++;
                    }
                    return mIterator;
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
    }

    @Override
    public char reduce(char identity, CharBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        char current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.applyAsChar(current, mIterator.nextChar());
        }
        return current;
    }

    @Override
    public OptionalCharacter reduce(CharBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalCharacter.empty();
        }

        char current = mIterator.nextChar();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsChar(current, mIterator.nextChar());
        }
        return OptionalCharacter.of(current);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, ObjCharConsumer<R> accumulator) {
        Utils.requireNonNull(supplier);
        Utils.requireNonNull(accumulator);

        final R sink = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(sink, mIterator.nextChar());
        }
        return sink;
    }

    @Override
    public <E extends Throwable> CharacterExceptional<E> exception(Class<E> cls) {
        return new CharacterExceptionalImpl<>(cls);
    }

    public char sum() {
        return reduce((char) 0, (a, b) -> (char) (a + b));
    }

    @Override
    public OptionalCharacter min() {
        return reduce((a, b) -> a < b ? a : b);
    }

    @Override
    public OptionalCharacter max() {
        return reduce((a, b) -> a < b ? b : a);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
    public CharacterStream sort() {
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> {
                    final char[] array = toArray();
                    Arrays.sort(array);
                    return new CharArrayIterator(array);
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
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
    public OptionalCharacter findFirst() {
        return mIterator.hasNext()
                ? OptionalCharacter.of(mIterator.nextChar())
                : OptionalCharacter.empty();
    }

    @Override
    public boolean anyMatch(CharPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextChar())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean allMatch(CharPredicate predicate) {
        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.nextChar())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean noneMatch(CharPredicate predicate) {
        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.nextChar())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public char[] toArray() {
        char[] tmp = new char[DEFAULT_ARRAY_SIZE];
        int index = 0;
        while (mIterator.hasNext()) {
            final char c = mIterator.nextChar();

            if (index >= tmp.length) {
                final char[] newArray = new char[tmp.length * 2];
                System.arraycopy(tmp, 0, newArray, 0, tmp.length);
                tmp = newArray;
            }

            tmp[index++] = c;
        }

        final char[] result = new char[index];
        System.arraycopy(tmp, 0, result, 0, index);
        return result;
    }

    private static class DummyIterator extends BaseIterator<Character> implements CharIterator {

        private char mValue;
        private boolean mHasNext;

        public DummyIterator(char value) {
            this(value, true);
        }

        public DummyIterator() {
            this('a', false);
        }

        private DummyIterator(char value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator newValue(char value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public char nextChar() {
            mHasNext = false;
            return mValue;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public Character next() {
            return nextChar();
        }
    }

    private class CharacterExceptionalImpl<E extends Throwable> implements CharacterExceptional<E> {

        private final Class<E> mExceptionClass;

        public CharacterExceptionalImpl(Class<E> exceptionClass) {
            mExceptionClass = exceptionClass;
        }

        @Override
        public CharacterStream mapException(ToCharFunction<E> mapper) {
            mIteratorWrapper.mapException(mExceptionClass, mapper);
            return CharacterStreamImpl.this;
        }

        @Override
        public CharacterStream consume(Consumer<E> consumer) {
            mIteratorWrapper.consumeException(mExceptionClass, consumer);
            return CharacterStreamImpl.this;
        }

        @Override
        public <I extends RuntimeException> CharacterStream rethrow(Function<E, I> mapper) {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
                throw mapper.apply(e);
            });
            return CharacterStreamImpl.this;
        }

        @Override
        public CharacterStream ignore() {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
            });
            return CharacterStreamImpl.this;
        }
    }
}
