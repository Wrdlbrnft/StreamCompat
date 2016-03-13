package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class LongArrayIterator implements Iterator<Long> {

    private final long[] mArray;
    private int mIndex = 0;

    LongArrayIterator(long[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Long next() {
        return mArray[mIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
