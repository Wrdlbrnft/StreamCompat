package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class PredicateIterator<T> implements Iterator<T> {

    private final Iterator<T> mBaseIterator;
    private final Predicate<T> mPredicate;
    private T mNext;
    private boolean mHasNext;

    PredicateIterator(Iterator<T> baseIterator, Predicate<T> predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final T item = mBaseIterator.next();
            if (mPredicate.test(item)) {
                mNext = item;
                mHasNext = true;
                return;
            }
        }
        mNext = null;
        mHasNext = false;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }

    @Override
    public T next() {
        final T current = mNext;
        moveToNext();
        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
