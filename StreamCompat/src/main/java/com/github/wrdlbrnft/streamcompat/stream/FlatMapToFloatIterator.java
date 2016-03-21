package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMapToFloatIterator<I> extends BaseFloatIterator {

    private final Iterator<I> mBaseIterator;
    private final Function<? super I, ? extends FloatStream> mMapper;

    private FloatIterator mChild = null;
    private boolean mHasNext;
    private float mNext;

    public FlatMapToFloatIterator(Iterator<I> iterator, Function<? super I, ? extends FloatStream> mapper) {
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
            final I input = mBaseIterator.next();
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
