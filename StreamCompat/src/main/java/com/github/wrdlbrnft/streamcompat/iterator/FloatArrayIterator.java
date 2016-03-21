package com.github.wrdlbrnft.streamcompat.iterator;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class FloatArrayIterator extends BaseIterator<Float> implements FloatIterator {

    private final float[] mArray;
    private int mIndex = 0;

    public FloatArrayIterator(float[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Float next() {
        return nextFloat();
    }

    @Override
    public float nextFloat() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
