package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class BooleanArrayIterator implements Iterator<Boolean> {

    private final boolean[] mArray;
    private int mIndex = 0;

    BooleanArrayIterator(boolean[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Boolean next() {
        return mArray[mIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
