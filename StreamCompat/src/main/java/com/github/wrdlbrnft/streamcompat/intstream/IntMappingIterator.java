package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.function.IntUnaryOperator;
import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

/**
 * Created by kapeller on 10/03/16.
 */
class IntMappingIterator extends BaseIntIterator implements IntIterator {

    private final IntIterator mBaseIterator;
    private final IntUnaryOperator mMapper;

    public IntMappingIterator(IntIterator iterator, IntUnaryOperator mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final int next = mBaseIterator.nextInt();
        return mMapper.applyAsInt(next);
    }
}
