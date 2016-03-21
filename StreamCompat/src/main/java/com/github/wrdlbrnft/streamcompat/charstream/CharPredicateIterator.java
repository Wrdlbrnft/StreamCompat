package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.function.CharPredicate;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class CharPredicateIterator extends BaseCharIterator implements CharIterator {

    private final CharIterator mBaseIterator;
    private final CharPredicate mPredicate;
    private char mNext;
    private boolean mHasNext;

    CharPredicateIterator(CharIterator baseIterator, CharPredicate predicate) {
        mBaseIterator = baseIterator;
        mPredicate = predicate;
        moveToNext();
    }

    private void moveToNext() {
        while (mBaseIterator.hasNext()) {
            final char item = mBaseIterator.next();
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
    public char nextChar() {
        if (!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final char current = mNext;
        moveToNext();
        return current;
    }
}
