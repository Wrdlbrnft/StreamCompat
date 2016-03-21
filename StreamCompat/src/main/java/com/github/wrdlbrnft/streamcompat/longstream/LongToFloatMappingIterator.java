package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongToFloatMappingIterator extends BaseFloatIterator {

    private final LongIterator mIterator;
    private final LongToFloatFunction mMapper;

    public LongToFloatMappingIterator(LongIterator iterator, LongToFloatFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final long value = mIterator.nextLong();
        return mMapper.applyAsFloat(value);
    }
}
