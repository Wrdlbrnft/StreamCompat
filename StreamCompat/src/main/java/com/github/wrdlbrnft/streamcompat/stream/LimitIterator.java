package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
class LimitIterator<T> extends BaseIterator<T> implements Iterator<T> {

    private final Iterator<T> mBaseIterator;
    private final long mLimit;
    private long mIndex = 0;

    public LimitIterator(Iterator<T> baseIterator, long limit) {
        mBaseIterator = baseIterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mBaseIterator.hasNext();
    }

    @Override
    public T next() {
        mIndex++;
        return mBaseIterator.next();
    }
}
