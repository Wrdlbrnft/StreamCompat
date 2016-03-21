package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class MapToDoubleIterator<T> extends BaseDoubleIterator {

    private final Iterator<T> mIterator;
    private final ToDoubleFunction<? super T> mMapper;

    public MapToDoubleIterator(Iterator<T> iterator, ToDoubleFunction<? super T> mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final T item = mIterator.next();
        return mMapper.apply(item);
    }
}
