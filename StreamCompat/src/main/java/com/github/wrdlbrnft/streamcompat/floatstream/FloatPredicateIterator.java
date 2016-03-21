package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatPredicate;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FloatPredicateIterator extends BaseFloatIterator implements FloatIterator {

    private final FloatIterator mBaseIterator;
    private final FloatPredicate mPredicate;
    private float mNext;
    private boolean mHasNext;

    FloatPredicateIterator(FloatIterator baseIterator, FloatPredicate predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final float item = mBaseIterator.next();
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
    public float nextFloat() {
        if (!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final float current = mNext;
        moveToNext();
        return current;
    }
}
