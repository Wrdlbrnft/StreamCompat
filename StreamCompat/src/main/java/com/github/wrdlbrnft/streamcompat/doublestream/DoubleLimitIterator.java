package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleLimitIterator extends BaseDoubleIterator implements DoubleIterator {

    private final DoubleIterator mIterator;
    private final long mLimit;
    private long mIndex = 0;

    public DoubleLimitIterator(DoubleIterator iterator, long limit) {
        mIterator = iterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        mIndex++;
        return mIterator.nextDouble();
    }
}
