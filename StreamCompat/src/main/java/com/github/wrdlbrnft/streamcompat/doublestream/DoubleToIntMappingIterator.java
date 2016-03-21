package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleToIntMappingIterator extends BaseIntIterator {

    private final DoubleIterator mIterator;
    private final DoubleToIntFunction mMapper;

    DoubleToIntMappingIterator(DoubleIterator iterator, DoubleToIntFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final double value = mIterator.nextDouble();
        return mMapper.applyAsInt(value);
    }
}
