package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatToDoubleMappingIterator extends BaseDoubleIterator {

    private final FloatIterator mIterator;
    private final FloatToDoubleFunction mMapper;

    FloatToDoubleMappingIterator(FloatIterator iterator, FloatToDoubleFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final float value = mIterator.nextFloat();
        return mMapper.applyAsDouble(value);
    }
}
