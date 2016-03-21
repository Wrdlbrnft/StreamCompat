package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.function.FloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class FloatToObjectMappingIterator<O> extends BaseIterator<O> implements Iterator<O> {

    private final FloatIterator mBaseIterator;
    private final FloatFunction<? extends O> mMapper;

    public FloatToObjectMappingIterator(FloatIterator iterator, FloatFunction<? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final float next = mBaseIterator.nextFloat();
        return mMapper.apply(next);
    }
}
