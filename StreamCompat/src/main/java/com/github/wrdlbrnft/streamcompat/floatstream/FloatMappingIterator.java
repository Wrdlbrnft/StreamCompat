package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatUnaryOperator;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 10/03/16.
 */
class FloatMappingIterator extends BaseFloatIterator implements FloatIterator {

    private final FloatIterator mBaseIterator;
    private final FloatUnaryOperator mMapper;

    public FloatMappingIterator(FloatIterator iterator, FloatUnaryOperator mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final float next = mBaseIterator.nextFloat();
        return mMapper.applyAsFloat(next);
    }
}
