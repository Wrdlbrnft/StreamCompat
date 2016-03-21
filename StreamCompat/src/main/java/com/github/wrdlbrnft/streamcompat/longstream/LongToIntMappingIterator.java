package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class LongToIntMappingIterator extends BaseIntIterator {

    private final LongIterator mIterator;
    private final LongToIntFunction mMapper;

    public LongToIntMappingIterator(LongIterator iterator, LongToIntFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final long value = mIterator.nextLong();
        return mMapper.applyAsInt(value);
    }
}
