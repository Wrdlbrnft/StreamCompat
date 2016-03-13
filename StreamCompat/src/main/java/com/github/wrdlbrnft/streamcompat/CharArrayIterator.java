package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class CharArrayIterator implements Iterator<Character> {

    private final char[] mArray;
    private int mIndex = 0;

    CharArrayIterator(char[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Character next() {
        return mArray[mIndex++];
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
