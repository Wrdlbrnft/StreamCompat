package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleToFloatMappingIterator extends BaseFloatIterator {

    private final DoubleIterator mIterator;
    private final DoubleToFloatFunction mMapper;

    DoubleToFloatMappingIterator(DoubleIterator iterator, DoubleToFloatFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final double value = mIterator.nextDouble();
        return mMapper.applyAsFloat(value);
    }
}
