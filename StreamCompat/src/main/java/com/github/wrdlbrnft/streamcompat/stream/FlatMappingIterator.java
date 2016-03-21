package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMappingIterator<I, O> extends BaseIterator<O> implements Iterator<O> {

    private final Iterator<I> mBaseIterator;
    private final Function<I, ? extends Stream<? extends O>> mMapper;

    private Iterator<? extends O> mChild = null;
    private boolean mHasNext;
    private O mNext;

    public FlatMappingIterator(Iterator<I> iterator, Function<I, ? extends Stream<? extends O>> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.next();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final I input = mBaseIterator.next();
            final Stream<? extends O> stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.next();
                return;
            }
        }
        mNext = null;
        mHasNext = false;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }

    @Override
    public O next() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final O current = mNext;
        moveToNext();
        return current;
    }
}
