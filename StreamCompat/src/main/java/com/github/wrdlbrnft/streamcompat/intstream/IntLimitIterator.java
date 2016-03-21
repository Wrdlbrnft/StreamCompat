package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
class IntLimitIterator extends BaseIntIterator implements IntIterator {

    private final IntIterator mIterator;
    private final long mLimit;
    private long mIndex = 0;

    public IntLimitIterator(IntIterator iterator, long limit) {
        mIterator = iterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        mIndex++;
        return mIterator.nextInt();
    }
}
