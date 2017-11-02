package com.github.wrdlbrnft.streamcompat.stream;

import android.support.annotation.NonNull;
import android.support.v4.util.ArraySet;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStream;
import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStream;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.function.BiConsumer;
import com.github.wrdlbrnft.streamcompat.function.BinaryOperator;
import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToByteFunction;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
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
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.optionals.Optional;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 10/03/16
 */
class StreamImpl<T> implements Stream<T> {

    private final Iterator<T> mIterator;
    private final IteratorWrapper<T> mIteratorWrapper = new IteratorWrapperImpl<>();

    StreamImpl(Iterator<T> iterator) {
        mIterator = mIteratorWrapper.apply(iterator);
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        Utils.requireNonNull(predicate);

        final DummyIterator<T> dummy = new DummyIterator<>();
        return new StreamImpl<>(new ChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final T value = mIterator.next();
                        if (predicate.test(value)) {
                            return dummy.newValue(value);
                        }
                    }
                    return dummy;
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    @Override
    public <R> Stream<R> map(Function<T, ? extends R> mapper) {
        Utils.requireNonNull(mapper);
        return new StreamImpl<>(new ChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(mIterator.next())
        ));
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public FloatStream mapToFloat(ToFloatFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public CharacterStream mapToChar(ToCharFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public ByteStream mapToByte(ToByteFunction<? super T> mapper) {
        Utils.requireNonNull(mapper);
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> mIterator,
                Iterator::hasNext,
                i -> mapper.apply(i.next())
        ));
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator[] buffer = new Iterator[1];
        return new StreamImpl<>(new ChildIterator<R, R, Iterator<R>>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return Utils.emptyIterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        Utils.requireNonNull(mapper);
        final IntIterator[] buffer = new IntIterator[1];
        return IntStreamCompat.of(new IntChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return IntStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                IntIterator::hasNext,
                IntIterator::nextInt
        ));
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        Utils.requireNonNull(mapper);
        final LongIterator[] buffer = new LongIterator[1];
        return LongStreamCompat.of(new LongChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return LongStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                LongIterator::hasNext,
                LongIterator::nextLong
        ));
    }

    @Override
    public FloatStream flatMapToFloat(Function<? super T, ? extends FloatStream> mapper) {
        Utils.requireNonNull(mapper);
        final FloatIterator[] buffer = new FloatIterator[1];
        return FloatStreamCompat.of(new FloatChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return FloatStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                FloatIterator::hasNext,
                FloatIterator::nextFloat
        ));
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        Utils.requireNonNull(mapper);
        final DoubleIterator[] buffer = new DoubleIterator[1];
        return DoubleStreamCompat.of(new DoubleChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return DoubleStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
    }

    @Override
    public CharacterStream flatMapToChar(Function<? super T, ? extends CharacterStream> mapper) {
        Utils.requireNonNull(mapper);
        final CharIterator[] buffer = new CharIterator[1];
        return CharacterStreamCompat.of(new CharChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return CharacterStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
    }

    @Override
    public ByteStream flatMapToByte(Function<? super T, ? extends ByteStream> mapper) {
        Utils.requireNonNull(mapper);
        final ByteIterator[] buffer = new ByteIterator[1];
        return ByteStreamCompat.of(new ByteChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!mIterator.hasNext()) {
                            return ByteStreamCompat.empty().iterator();
                        }
                        buffer[0] = mapper.apply(mIterator.next()).iterator();
                    }
                    return buffer[0];
                },
                ByteIterator::hasNext,
                ByteIterator::nextByte
        ));
    }

    @Override
    public Stream<T> distinct(Supplier<Set<T>> setSupplier) {
        Utils.requireNonNull(setSupplier);
        final Set<T> set = setSupplier.get();
        final DummyIterator<T> dummy = new DummyIterator<>();
        return new StreamImpl<>(new ChildIterator<>(
                () -> {
                    while (mIterator.hasNext()) {
                        final T value = mIterator.next();
                        if (set.add(value)) {
                            return dummy.newValue(value);
                        }
                    }
                    return dummy;
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    @Override
    public Stream<T> distinct() {
        return distinct(ArraySet::new);
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> function) {
        Utils.requireNonNull(function);
        final A result = function.supplier().get();

        while (mIterator.hasNext()) {
            function.accumulator().accept(result, mIterator.next());
        }

        return function.finisher().apply(result);
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator) {
        Utils.requireNonNull(accumulator);
        Utils.requireNonNull(supplier);

        final R result = supplier.get();
        while (mIterator.hasNext()) {
            accumulator.accept(result, mIterator.next());
        }
        return result;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        final long[] buffer = {0, maxSize};
        return new StreamImpl<>(new ChildIterator<>(
                () -> mIterator,
                i -> buffer[0] < buffer[1] && i.hasNext(),
                i -> {
                    buffer[0]++;
                    return i.next();
                }
        ));
    }

    @Override
    public <E extends Throwable> Exceptional<T, E> exception(Class<E> cls) {
        Utils.requireNonNull(cls);
        return new ExceptionalImpl<>(cls);
    }

    @Override
    public Stream<T> skip(long count) {
        final long[] buffer = {0, count};
        return new StreamImpl<>(new ChildIterator<>(
                () -> {
                    while (mIterator.hasNext() && buffer[0] < buffer[1]) {
                        mIterator.next();
                        buffer[0]++;
                    }
                    return mIterator;
                },
                Iterator::hasNext,
                Iterator::next
        ));
    }

    @Override
    public Stream<T> sort(Comparator<T> comparator) {
        Utils.requireNonNull(comparator);
        return new StreamImpl<>(new ChildIterator<>(
                () -> collect(Collectors.toOrderedList(comparator)).iterator(),
                Iterator::hasNext,
                Iterator::next
        ));
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        if (!mIterator.hasNext()) {
            return Optional.empty();
        }

        return Optional.of(reduce(mIterator.next(), accumulator));
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        return reduce(identity, i -> i, accumulator);
    }

    @Override
    public <U> U reduce(U identity, Function<? super T, ? extends U> mapper, BinaryOperator<U> accumulator) {
        Utils.requireNonNull(mapper);
        Utils.requireNonNull(accumulator);

        U current = identity;
        while (mIterator.hasNext()) {
            current = accumulator.apply(current, mapper.apply(mIterator.next()));
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
    public T[] toArray(IntFunction<T[]> generator) {
        Utils.requireNonNull(generator);

        return collect(Collectors.toArray(generator));
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

    @NonNull
    @Override
    public Iterator<T> iterator() {
        return mIterator;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        Utils.requireNonNull(action);

        while (mIterator.hasNext()) {
            final T item = mIterator.next();
            action.accept(item);
        }
    }

    private static class DummyIterator<T> extends BaseIterator<T> {

        private T mValue;
        private boolean mHasNext;

        public DummyIterator(T value) {
            this(value, true);
        }

        public DummyIterator() {
            this(null, false);
        }

        private DummyIterator(T value, boolean hasNext) {
            mValue = value;
            mHasNext = hasNext;
        }

        public DummyIterator<T> newValue(T value) {
            mValue = value;
            mHasNext = true;
            return this;
        }

        @Override
        public boolean hasNext() {
            return mHasNext;
        }

        @Override
        public T next() {
            mHasNext = false;
            return mValue;
        }
    }

    private class ExceptionalImpl<E extends Throwable> implements Exceptional<T, E> {

        private final Class<E> mExceptionClass;

        ExceptionalImpl(Class<E> cls) {
            mExceptionClass = cls;
        }

        @Override
        public Stream<T> consume(Consumer<E> consumer) {
            mIteratorWrapper.consumeException(mExceptionClass, consumer);
            return StreamImpl.this;
        }

        @Override
        public <I extends RuntimeException> Stream<T> rethrow(Function<E, I> mapper) {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
                throw mapper.apply(e);
            });
            return StreamImpl.this;
        }

        @Override
        public Stream<T> ignore() {
            mIteratorWrapper.consumeException(mExceptionClass, e -> {
            });
            return StreamImpl.this;
        }

        @Override
        public Stream<T> mapException(Function<E, T> mapper) {
            mIteratorWrapper.mapException(mExceptionClass, mapper);
            return StreamImpl.this;
        }
    }
}
