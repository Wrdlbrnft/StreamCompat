package com.github.wrdlbrnft.streamcompat.iterator.array;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 10/03/16
 */
public class ArrayIterator<T> extends BaseIterator<T> implements Iterator<T> {

    private final T[] mArray;
    private int mIndex = 0;

    public ArrayIterator(T[] array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.length;
    }

    @Override
    public T next() {
        if (mIndex >= mArray.length) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        return mArray[mIndex++];
    }
}
