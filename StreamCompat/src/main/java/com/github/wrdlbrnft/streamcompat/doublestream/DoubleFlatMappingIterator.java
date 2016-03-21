package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class DoubleFlatMappingIterator extends BaseDoubleIterator implements DoubleIterator {

    private final DoubleIterator mBaseIterator;
    private final DoubleFunction<? extends DoubleStream> mMapper;

    private DoubleIterator mChild = null;
    private boolean mHasNext;
    private double mNext;

    public DoubleFlatMappingIterator(DoubleIterator iterator, DoubleFunction<? extends DoubleStream> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.nextDouble();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final double input = mBaseIterator.nextDouble();
            final DoubleStream stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.nextDouble();
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
    public double nextDouble() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final double current = mNext;
        moveToNext();
        return current;
    }
}
