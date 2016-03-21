package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.charstream.CharStream;
import com.github.wrdlbrnft.streamcompat.charstream.CharStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.util.Optional;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class StreamImpl<T> implements Stream<T> {

    private final Iterator<T> mIterator;

    public StreamImpl(Iterator<T> iterator) {
        mIterator = iterator;
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        Utils.requireNonNull(predicate);
        final Iterator<T> iterator = new PredicateIterator<>(mIterator, predicate);
        return new StreamImpl<>(iterator);
    }

    @Override
    public <R> Stream<R> map(Function<T, ? extends R> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<R> iterator = new MappingIterator<>(mIterator, mapper);
        return new StreamImpl<>(iterator);
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new MapToIntIterator<>(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new MapToLongIterator<>(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new MapToDoubleIterator<>(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public FloatStream mapToFloat(ToFloatFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new MapToFloatIterator<>(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public CharStream mapToChar(ToCharFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new MapToCharIterator<>(mIterator, mapper);
        return CharStreamCompat.of(iterator);
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<R> iterator = new FlatMappingIterator<>(mIterator, mapper);
        return new StreamImpl<>(iterator);
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator iterator = new FlatMapToIntIterator<>(mIterator, mapper);
        return IntStreamCompat.of(iterator);
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator iterator = new FlatMapToLongIterator<>(mIterator, mapper);
        return LongStreamCompat.of(iterator);
    }

    @Override
    public FloatStream flatMapToFloat(Function<? super T, ? extends FloatStream> mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator iterator = new FlatMapToFloatIterator<>(mIterator, mapper);
        return FloatStreamCompat.of(iterator);
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator iterator = new FlatMapToDoubleIterator<>(mIterator, mapper);
        return DoubleStreamCompat.of(iterator);
    }

    @Override
    public CharStream flatMapToChar(Function<? super T, ? extends CharStream> mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator iterator = new FlatMapToCharIterator<>(mIterator, mapper);
        return CharStreamCompat.of(iterator);
    }

    @Override
    public <A, R> R collect(Collector<T, A, R> function) {
        Utils.requireNonNull(function);
        final A result = function.supplier().get();

        while (mIterator.hasNext()) {
            function.accumulator().accept(result, mIterator.next());
        }

        return function.finisher().apply(result);
    }

    @Override
    public Stream<T> limit(long limit) {
        final Iterator<T> iterator = new LimitIterator<>(mIterator, limit);
        return new StreamImpl<>(iterator);
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Utils.requireNonNull(accumulator);

        T current = mIterator.hasNext() ? mIterator.next() : null;
        while (mIterator.hasNext()) {
            current = accumulator.apply(current, mIterator.next());
        }

        return Optional.of(current);
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        Utils.requireNonNull(accumulator);

        T current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.apply(current, mIterator.next());
        }

        return current;
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        Utils.requireNonNull(comparator);
        return reduce((a, b) -> comparator.compare(a, b) < 0 ? a : b);
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        Utils.requireNonNull(comparator);
        return reduce((a, b) -> comparator.compare(a, b) < 0 ? b : a);
    }

    @Override
    public long count() {
        return mapToLong(i -> 1L).sum();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        Utils.requireNonNull(predicate);

        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.next())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        Utils.requireNonNull(predicate);

        while (mIterator.hasNext()) {
            if (!predicate.test(mIterator.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        Utils.requireNonNull(predicate);

        while (mIterator.hasNext()) {
            if (predicate.test(mIterator.next())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Optional<T> findFirst() {
        while (mIterator.hasNext()) {
            final T item = mIterator.next();
            if (item != null) {
                return Optional.of(item);
            }
        }

        return Optional.empty();
    }

    @Override
    public Iterator<T> iterator() {
        return mIterator;
    }
}
