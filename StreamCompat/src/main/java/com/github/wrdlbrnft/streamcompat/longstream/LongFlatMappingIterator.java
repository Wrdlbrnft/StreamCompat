package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class LongFlatMappingIterator extends BaseIterator<Long> implements LongIterator {

    private final LongIterator mBaseIterator;
    private final LongFunction<? extends LongStream> mMapper;

    private LongIterator mChild = null;
    private boolean mHasNext;
    private long mNext;

    public LongFlatMappingIterator(LongIterator iterator, LongFunction<? extends LongStream> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.nextLong();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final long input = mBaseIterator.nextLong();
            final LongStream stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.nextLong();
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
    public Long next() {
        return nextLong();
    }

    @Override
    public long nextLong() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final long current = mNext;
        moveToNext();
        return current;
    }
}
