package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleToLongMappingIterator extends BaseLongIterator {

    private final DoubleIterator mIterator;
    private final DoubleToLongFunction mMapper;

    DoubleToLongMappingIterator(DoubleIterator iterator, DoubleToLongFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        final double value = mIterator.nextDouble();
        return mMapper.applyAsLong(value);
    }
}
