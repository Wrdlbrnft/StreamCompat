package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.CharBinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.function.CharPredicate;
import com.github.wrdlbrnft.streamcompat.function.CharToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.CharToLongFunction;
import com.github.wrdlbrnft.streamcompat.function.CharUnaryOperator;
import com.github.wrdlbrnft.streamcompat.function.ObjCharConsumer;
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
import com.github.wrdlbrnft.streamcompat.util.OptionalChar;
import com.github.wrdlbrnft.streamcompat.util.OptionalDouble;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharStreamImpl implements CharStream {

    private final CharIterator mIterator;

    CharStreamImpl(CharIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public CharStream filter(CharPredicate predicate) {
        Utils.requireNonNull(predicate);
        final CharIterator iterator = new CharPredicateIterator(mIterator, predicate);
        return new CharStreamImpl(iterator);
    }

    @Override
    public CharStream map(CharUnaryOperator mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new CharMappingIterator(mIterator, mapper);
        return new CharStreamImpl(iterator);
    }

    @Override
    public CharStream flatMap(CharFunction<? extends CharStream> mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new CharFlatMappingIterator(mIterator, mapper);
        return new CharStreamImpl(iterator);
    }

    @Override
    public <U> Stream<U> mapToObj(CharFunction<? extends U> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<U> iterator = new CharToObjectMappingIterator<>(mIterator, mapper);
        return StreamCompat.of(iterator);
    }

    @Override
    public LongStream mapToLong(CharToLongFunction mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new CharToLongMappingIterator(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public IntStream mapToInt(CharToIntFunction mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new CharToIntMappingIterator(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream mapToDouble(CharToDoubleFunction mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new CharToDoubleMappingIterator(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public FloatStream mapToFloat(CharToFloatFunction mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new CharToFloatMappingIterator(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public CharIterator iterator() {
        return mIterator;
    }

    @Override
    public Stream<Character> boxed() {
        return StreamCompat.of(mIterator);
    }

    @Override
    public CharStream limit(long limit) {
        final CharIterator iterator = new CharLimitIterator(mIterator, limit);
        return new CharStreamImpl(iterator);
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
    public OptionalChar reduce(CharBinaryOperator accumulator) {
        Utils.requireNonNull(accumulator);
        if (!mIterator.hasNext()) {
            return OptionalChar.empty();
        }

        char current = mIterator.nextChar();
        while (mIterator.hasNext()) {
            current = accumulator.applyAsChar(current, mIterator.nextChar());
        }
        return OptionalChar.of(current);
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
    public char sum() {
        return reduce((char) 0, (a, b) -> (char) (a + b));
    }

    @Override
    public OptionalChar min() {
        return reduce((a, b) -> a < b ? a : b);
    }

    @Override
    public OptionalChar max() {
        return reduce((a, b) -> a < b ? b : a);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
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
    public OptionalChar findFirst() {
        return mIterator.hasNext()
                ? OptionalChar.of(mIterator.nextChar())
                : OptionalChar.empty();
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
}
