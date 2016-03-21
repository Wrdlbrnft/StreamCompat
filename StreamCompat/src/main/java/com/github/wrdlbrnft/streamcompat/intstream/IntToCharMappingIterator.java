package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class IntToCharMappingIterator extends BaseCharIterator {

    private final IntIterator mIterator;
    private final IntToCharFunction mMapper;

    IntToCharMappingIterator(IntIterator iterator, IntToCharFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final int value = mIterator.nextInt();
        return mMapper.applyAsChar(value);
    }
}
