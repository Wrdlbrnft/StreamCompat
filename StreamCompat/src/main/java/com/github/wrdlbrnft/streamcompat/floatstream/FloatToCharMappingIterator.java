package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class FloatToCharMappingIterator extends BaseCharIterator {

    private final FloatIterator mIterator;
    private final FloatToCharFunction mMapper;

    FloatToCharMappingIterator(FloatIterator iterator, FloatToCharFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final float value = mIterator.nextFloat();
        return mMapper.applyAsChar(value);
    }
}
