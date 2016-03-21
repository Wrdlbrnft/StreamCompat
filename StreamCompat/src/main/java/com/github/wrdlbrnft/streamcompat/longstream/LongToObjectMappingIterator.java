package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class LongToObjectMappingIterator<O> extends BaseIterator<O> implements Iterator<O> {

    private final LongIterator mBaseIterator;
    private final LongFunction<? extends O> mMapper;

    public LongToObjectMappingIterator(LongIterator iterator, LongFunction<? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final long next = mBaseIterator.nextLong();
        return mMapper.apply(next);
    }
}
