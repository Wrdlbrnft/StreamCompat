package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FloatFlatMappingIterator extends BaseFloatIterator implements FloatIterator {

    private final FloatIterator mBaseIterator;
    private final FloatFunction<? extends FloatStream> mMapper;

    private FloatIterator mChild = null;
    private boolean mHasNext;
    private float mNext;

    public FloatFlatMappingIterator(FloatIterator iterator, FloatFunction<? extends FloatStream> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.nextFloat();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final float input = mBaseIterator.nextFloat();
            final FloatStream stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.nextFloat();
                return;
            }
        }
        mHasNext = false;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }

    @Override
    public float nextFloat() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final float current = mNext;
        moveToNext();
        return current;
    }
}
