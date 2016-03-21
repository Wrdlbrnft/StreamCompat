package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntToFloatMappingIterator extends BaseFloatIterator {

    private final IntIterator mIterator;
    private final IntToFloatFunction mMapper;

    IntToFloatMappingIterator(IntIterator iterator, IntToFloatFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final int value = mIterator.nextInt();
        return mMapper.applyAsFloat(value);
    }
}
