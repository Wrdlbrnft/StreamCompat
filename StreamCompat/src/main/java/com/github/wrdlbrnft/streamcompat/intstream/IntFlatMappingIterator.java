package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class IntFlatMappingIterator extends BaseIntIterator implements IntIterator {

    private final IntIterator mBaseIterator;
    private final IntFunction<? extends IntStream> mMapper;

    private IntIterator mChild = null;
    private boolean mHasNext;
    private int mNext;

    public IntFlatMappingIterator(IntIterator iterator, IntFunction<? extends IntStream> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.nextInt();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final int input = mBaseIterator.nextInt();
            final IntStream stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.nextInt();
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
    public int nextInt() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final int current = mNext;
        moveToNext();
        return current;
    }
}
