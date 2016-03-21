package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class IntPredicateIterator extends BaseIntIterator implements IntIterator {

    private final IntIterator mBaseIterator;
    private final IntPredicate mPredicate;
    private int mNext;
    private boolean mHasNext;

    IntPredicateIterator(IntIterator baseIterator, IntPredicate predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final int item = mBaseIterator.next();
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
    public int nextInt() {
        if (!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final int current = mNext;
        moveToNext();
        return current;
    }
}
