package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoublePredicate;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class DoublePredicateIterator extends BaseDoubleIterator implements DoubleIterator {

    private final DoubleIterator mBaseIterator;
    private final DoublePredicate mPredicate;
    private double mNext;
    private boolean mHasNext;

    DoublePredicateIterator(DoubleIterator baseIterator, DoublePredicate predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final double item = mBaseIterator.next();
            if (mPredicate.test(item)) {
                mNext = item;
                mHasNext = true;
                return;
            }
        }
        mHasNext = false;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }

    @Override
    public double nextDouble() {
        if (!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final double current = mNext;
        moveToNext();
        return current;
    }
}
