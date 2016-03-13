package com.github.wrdlbrnft.streamcompat;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMappingIterator<I, O> implements Iterator<O> {

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
        final O current = mNext;
        moveToNext();
        return current;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
