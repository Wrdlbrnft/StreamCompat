package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongRangeIterator extends BaseLongIterator {

    private long mIndex;
    private final long mEnd;

    public LongRangeIterator(long start, long end) {
        mIndex = start;
        mEnd = end;
    }

    @Override
    public long nextLong() {
        return mIndex++;
    }

    @Override
    public boolean hasNext() {
        return mIndex <= mEnd;
    }
}
