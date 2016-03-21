package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntToLongMappingIterator extends BaseLongIterator {

    private final IntIterator mIterator;
    private final IntToLongFunction mMapper;

    IntToLongMappingIterator(IntIterator iterator, IntToLongFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        final int value = mIterator.nextInt();
        return mMapper.applyAsLong(value);
    }
}
