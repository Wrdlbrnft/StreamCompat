package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongPredicate;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class LongPredicateIterator extends BaseIterator<Long> implements LongIterator {

    private final LongIterator mBaseIterator;
    private final LongPredicate mPredicate;
    private long mNext;
    private boolean mHasNext;

    LongPredicateIterator(LongIterator baseIterator, LongPredicate predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final long item = mBaseIterator.next();
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
    public Long next() {
        return nextLong();
    }

    @Override
    public long nextLong() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final long current = mNext;
        moveToNext();
        return current;
    }
}
