package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongRangeIterator extends BaseIterator<Long> implements LongIterator {

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

    @Override
    public Long next() {
        return nextLong();
    }
}
