package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatToLongMappingIterator extends BaseLongIterator {

    private final FloatIterator mIterator;
    private final FloatToLongFunction mMapper;

    FloatToLongMappingIterator(FloatIterator iterator, FloatToLongFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        final float value = mIterator.nextFloat();
        return mMapper.applyAsLong(value);
    }
}
