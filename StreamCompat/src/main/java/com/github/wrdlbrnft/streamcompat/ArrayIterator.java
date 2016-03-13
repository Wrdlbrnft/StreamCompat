package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class ArrayIterator<T> implements Iterator<T> {

    private final T[] mArray;
    private int mIndex = 0;

    ArrayIterator(T[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public T next() {
        return mArray[mIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
