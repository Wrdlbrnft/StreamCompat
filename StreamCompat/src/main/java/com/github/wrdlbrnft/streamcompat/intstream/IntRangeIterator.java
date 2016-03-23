package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntRangeIterator extends BaseIterator<Integer> implements IntIterator {

    private int mIndex;
    private final int mEnd;

    public IntRangeIterator(int start, int end) {
        mIndex = start;
        mEnd = end;
    }

    @Override
    public int nextInt() {
        return mIndex++;
    }

    @Override
    public boolean hasNext() {
        return mIndex <= mEnd;
    }

    @Override
    public Integer next() {
        return nextInt();
    }
}
