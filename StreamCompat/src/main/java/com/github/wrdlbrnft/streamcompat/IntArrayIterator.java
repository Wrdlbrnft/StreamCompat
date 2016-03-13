package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class IntArrayIterator implements Iterator<Integer> {

    private final int[] mArray;
    private int mIndex = 0;

    IntArrayIterator(int[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Integer next() {
        return mArray[mIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
