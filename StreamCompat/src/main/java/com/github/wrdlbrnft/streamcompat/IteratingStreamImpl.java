package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class IteratingStreamImpl<T> implements Stream<T> {

    private final Iterator<T> mIterator;

    public IteratingStreamImpl(Iterator<T> iterator) {
        mIterator = iterator;
    }

    @Override
    public Stream<T> filter(Predicate<T> predicate) {
        Utils.requireNonNull(predicate);
        final Iterator<T> iterator = new PredicateIterator<>(mIterator, predicate);
        return new IteratingStreamImpl<>(iterator);
    }

    @Override
    public <R> Stream<R> map(Function<T, ? extends R> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<R> iterator = new MappingIterator<>(mIterator, mapper);
        return new IteratingStreamImpl<>(iterator);
    }

    @Override
    public <R> Stream<R> flatMap(Function<T, ? extends Stream<? extends R>> mapper) {
        Utils.requireNonNull(mapper);
        final Iterator<R> iterator = new FlatMappingIterator<>(mIterator, mapper);
        return new IteratingStreamImpl<>(iterator);
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
    public Iterator<T> iterator() {
        return mIterator;
    }
}
