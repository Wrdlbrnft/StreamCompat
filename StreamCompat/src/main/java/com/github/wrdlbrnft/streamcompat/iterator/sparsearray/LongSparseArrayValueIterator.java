package com.github.wrdlbrnft.streamcompat.iterator.sparsearray;

import android.support.v4.util.LongSparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 08/04/16
 */
public class LongSparseArrayValueIterator<T> extends BaseIterator<T> {

    private final LongSparseArray<T> mArray;
    private int mIndex = 0;

    public LongSparseArrayValueIterator(LongSparseArray<T> array) {
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
