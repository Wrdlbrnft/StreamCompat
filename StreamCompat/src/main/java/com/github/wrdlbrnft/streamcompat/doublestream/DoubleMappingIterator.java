package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleUnaryOperator;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 10/03/16.
 */
class DoubleMappingIterator extends BaseDoubleIterator implements DoubleIterator {

    private final DoubleIterator mBaseIterator;
    private final DoubleUnaryOperator mMapper;

    public DoubleMappingIterator(DoubleIterator iterator, DoubleUnaryOperator mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final double next = mBaseIterator.nextDouble();
        return mMapper.applyAsDouble(next);
    }
}
