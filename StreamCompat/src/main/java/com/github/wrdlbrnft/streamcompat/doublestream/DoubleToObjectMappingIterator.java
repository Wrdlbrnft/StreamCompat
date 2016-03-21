package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.function.DoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class DoubleToObjectMappingIterator<O> extends BaseIterator<O> implements Iterator<O> {

    private final DoubleIterator mBaseIterator;
    private final DoubleFunction<? extends O> mMapper;

    public DoubleToObjectMappingIterator(DoubleIterator iterator, DoubleFunction<? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final double next = mBaseIterator.nextDouble();
        return mMapper.apply(next);
    }
}
