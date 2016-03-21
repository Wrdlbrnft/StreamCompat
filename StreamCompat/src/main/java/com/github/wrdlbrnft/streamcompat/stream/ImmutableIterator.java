package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class ImmutableIterator<T> extends BaseIterator<T> implements Iterator<T> {

    private final Iterator<T> mIterator;

    public ImmutableIterator(Iterator<T> iterator) {
        mIterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public T next() {
        return mIterator.next();
    }
}
