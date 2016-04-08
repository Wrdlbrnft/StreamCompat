package com.github.wrdlbrnft.streamcompat.iterator.sparsearray;

import android.util.SparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 08/04/16
 */
public class SparseArrayKeyIterator<T> extends BaseIterator<Integer> implements IntIterator {

    private final SparseArray<T> mArray;
    private int mIndex = 0;

    public SparseArrayKeyIterator(SparseArray<T> array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.size();
    }

    @Override
    public int nextInt() {
        return mArray.keyAt(mIndex++);
    }

    @Override
    public Integer next() {
        return nextInt();
    }
}
