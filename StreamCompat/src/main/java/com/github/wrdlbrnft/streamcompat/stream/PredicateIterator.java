package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class PredicateIterator<T> extends BaseIterator<T> implements Iterator<T> {

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
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final T current = mNext;
        moveToNext();
        return current;
    }
}
