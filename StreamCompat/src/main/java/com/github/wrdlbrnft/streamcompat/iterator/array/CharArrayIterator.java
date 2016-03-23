package com.github.wrdlbrnft.streamcompat.iterator.array;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
public class CharArrayIterator extends BaseIterator<Character> implements CharIterator {

    private final char[] mArray;
    private int mIndex = 0;

    public CharArrayIterator(char[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public Character next() {
        return nextChar();
    }

    @Override
    public char nextChar() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
