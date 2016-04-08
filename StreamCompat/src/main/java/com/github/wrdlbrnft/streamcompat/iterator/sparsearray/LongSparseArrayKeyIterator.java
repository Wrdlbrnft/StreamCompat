package com.github.wrdlbrnft.streamcompat.iterator.sparsearray;

import android.support.v4.util.LongSparseArray;

import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 08/04/16
 */
public class LongSparseArrayKeyIterator<T> extends BaseIterator<Long> implements LongIterator {

    private final LongSparseArray<T> mArray;
    private int mIndex = 0;

    public LongSparseArrayKeyIterator(LongSparseArray<T> array) {
        mArray = array;
    }

    @Override
    public boolean hasNext() {
        return mIndex < mArray.size();
    }

    @Override
    public long nextLong() {
        return mArray.keyAt(mIndex++);
    }

    @Override
    public Long next() {
        return nextLong();
    }
}
