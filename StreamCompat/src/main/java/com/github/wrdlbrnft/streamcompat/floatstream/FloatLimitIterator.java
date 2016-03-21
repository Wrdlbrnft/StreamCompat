package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatLimitIterator extends BaseFloatIterator implements FloatIterator {

    private final FloatIterator mIterator;
    private final long mLimit;
    private long mIndex = 0;

    public FloatLimitIterator(FloatIterator iterator, long limit) {
        mIterator = iterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        mIndex++;
        return mIterator.nextFloat();
    }
}
