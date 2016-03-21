package com.github.wrdlbrnft.streamcompat.iterator;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class IntArrayIterator extends BaseIterator<Integer> implements IntIterator {

    private final int[] mArray;
    private int mIndex = 0;

    public IntArrayIterator(int[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Integer next() {
        return nextInt();
    }

    @Override
    public int nextInt() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
