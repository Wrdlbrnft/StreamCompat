package com.github.wrdlbrnft.streamcompat.iterator;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class DoubleArrayIterator extends BaseIterator<Double> implements DoubleIterator {

    private final double[] mArray;
    private int mIndex = 0;

    public DoubleArrayIterator(double[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Double next() {
        return nextDouble();
    }

    @Override
    public double nextDouble() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
