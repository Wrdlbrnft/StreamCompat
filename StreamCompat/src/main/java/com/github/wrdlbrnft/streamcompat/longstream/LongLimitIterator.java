package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongLimitIterator extends BaseLongIterator {

    private final LongIterator mIterator;
    private final long mLimit;
    private long mIndex = 0;

    public LongLimitIterator(LongIterator iterator, long limit) {
        mIterator = iterator;
        mLimit = limit;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mLimit && mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        mIndex++;
        return mIterator.nextLong();
    }
}
