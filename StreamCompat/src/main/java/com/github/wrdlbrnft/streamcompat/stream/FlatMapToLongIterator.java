package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.longstream.LongStream;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMapToLongIterator<I> extends BaseLongIterator {

    private final Iterator<I> mBaseIterator;
    private final Function<? super I, ? extends LongStream> mMapper;

    private LongIterator mChild = null;
    private boolean mHasNext;
    private long mNext;

    public FlatMapToLongIterator(Iterator<I> iterator, Function<? super I, ? extends LongStream> mapper) {
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
            final I input = mBaseIterator.next();
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
    public long nextLong() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final long current = mNext;
        moveToNext();
        return current;
    }
}
