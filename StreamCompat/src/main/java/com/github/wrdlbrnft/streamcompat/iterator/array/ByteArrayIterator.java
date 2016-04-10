package com.github.wrdlbrnft.streamcompat.iterator.array;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class ByteArrayIterator extends BaseIterator<Byte> implements ByteIterator {

    private final byte[] mArray;
    private int mIndex = 0;

    public ByteArrayIterator(byte[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Byte next() {
        return nextByte();
    }

    @Override
    public byte nextByte() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
