package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharLimitIterator extends BaseCharIterator implements CharIterator {

    private final CharIterator mIterator;
    private final long mLimit;
    private long mIndex = 0;

    public CharLimitIterator(CharIterator iterator, long limit) {
        mIterator = iterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        mIndex++;
        return mIterator.nextChar();
    }
}
