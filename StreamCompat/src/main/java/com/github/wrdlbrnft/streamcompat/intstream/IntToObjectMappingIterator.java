package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class IntToObjectMappingIterator<O> extends BaseIterator<O> implements Iterator<O> {

    private final IntIterator mBaseIterator;
    private final IntFunction<? extends O> mMapper;

    public IntToObjectMappingIterator(IntIterator iterator, IntFunction<? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final int next = mBaseIterator.nextInt();
        return mMapper.apply(next);
    }
}
