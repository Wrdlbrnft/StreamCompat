package com.github.wrdlbrnft.streamcompat.iterator.sparsearray;

import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 08/04/16
 */
public class SparseArrayValueIterator<T> extends BaseIterator<T> {

    private final SparseArray<T> mArray;
    private int mIndex = 0;

    public SparseArrayValueIterator(SparseArray<T> array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.size();
    }

    @Override
    public T next() {
        return mArray.valueAt(mIndex++);
    }
}
