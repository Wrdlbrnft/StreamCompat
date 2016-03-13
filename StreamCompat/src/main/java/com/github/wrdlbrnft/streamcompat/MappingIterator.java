package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class MappingIterator<I, O> implements Iterator<O> {

    private final Iterator<I> mBaseIterator;
    private final Function<I, ? extends O> mMapper;

    public MappingIterator(Iterator<I> iterator, Function<I, ? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final I next = mBaseIterator.next();
        return mMapper.apply(next);
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
