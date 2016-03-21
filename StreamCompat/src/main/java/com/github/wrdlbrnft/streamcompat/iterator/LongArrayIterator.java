package com.github.wrdlbrnft.streamcompat.iterator;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class LongArrayIterator extends BaseIterator<Long> implements LongIterator {

    private final long[] mArray;
    private int mIndex = 0;

    public LongArrayIterator(long[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Long next() {
        return nextLong();
    }

    @Override
    public long nextLong() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
