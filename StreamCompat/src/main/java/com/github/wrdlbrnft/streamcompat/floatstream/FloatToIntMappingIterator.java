package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatToIntMappingIterator extends BaseIntIterator {

    private final FloatIterator mIterator;
    private final FloatToIntFunction mMapper;

    FloatToIntMappingIterator(FloatIterator iterator, FloatToIntFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final float value = mIterator.nextFloat();
        return mMapper.applyAsInt(value);
    }
}
