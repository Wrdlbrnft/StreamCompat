package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.function.LongUnaryOperator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

/**
 * Created by kapeller on 10/03/16.
 */
class LongMappingIterator extends BaseIterator<Long> implements LongIterator {

    private final LongIterator mBaseIterator;
    private final LongUnaryOperator mMapper;

    public LongMappingIterator(LongIterator iterator, LongUnaryOperator mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public Long next() {
        return nextLong();
    }

    @Override
    public long nextLong() {
        final long next = mBaseIterator.nextLong();
        return mMapper.applyAsLong(next);
    }
}
