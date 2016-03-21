package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class MapToFloatIterator<T> extends BaseFloatIterator {

    private final Iterator<T> mIterator;
    private final ToFloatFunction<? super T> mMapper;

    public MapToFloatIterator(Iterator<T> iterator, ToFloatFunction<? super T> mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final T item = mIterator.next();
        return mMapper.apply(item);
    }
}
