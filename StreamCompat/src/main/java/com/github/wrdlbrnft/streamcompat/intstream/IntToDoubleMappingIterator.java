package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntToDoubleMappingIterator extends BaseDoubleIterator {

    private final IntIterator mIterator;
    private final IntToDoubleFunction mMapper;

    IntToDoubleMappingIterator(IntIterator iterator, IntToDoubleFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final int value = mIterator.nextInt();
        return mMapper.applyAsDouble(value);
    }
}
