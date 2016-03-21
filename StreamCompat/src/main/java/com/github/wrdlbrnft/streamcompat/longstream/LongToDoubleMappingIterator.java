package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongToDoubleMappingIterator extends BaseDoubleIterator {

    private final LongIterator mIterator;
    private final LongToDoubleFunction mMapper;

    public LongToDoubleMappingIterator(LongIterator iterator, LongToDoubleFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final long value = mIterator.nextLong();
        return mMapper.applyAsDouble(value);
    }
}
