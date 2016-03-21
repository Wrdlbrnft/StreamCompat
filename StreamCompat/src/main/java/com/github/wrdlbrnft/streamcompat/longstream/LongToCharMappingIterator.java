package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongToCharMappingIterator extends BaseCharIterator {

    private final LongIterator mIterator;
    private final LongToCharFunction mMapper;

    public LongToCharMappingIterator(LongIterator iterator, LongToCharFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final long value = mIterator.nextLong();
        return mMapper.applyAsChar(value);
    }
}
