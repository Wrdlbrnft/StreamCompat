package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMapToIntIterator<I> extends BaseIntIterator {

    private final Iterator<I> mBaseIterator;
    private final Function<? super I, ? extends IntStream> mMapper;

    private IntIterator mChild = null;
    private boolean mHasNext;
    private int mNext;

    public FlatMapToIntIterator(Iterator<I> iterator, Function<? super I, ? extends IntStream> mapper) {
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
            final I input = mBaseIterator.next();
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
        if (!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final int current = mNext;
        moveToNext();
        return current;
    }
}
