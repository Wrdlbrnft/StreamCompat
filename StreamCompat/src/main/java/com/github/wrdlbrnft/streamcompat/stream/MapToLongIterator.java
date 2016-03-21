package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class MapToLongIterator<T> extends BaseLongIterator {

    private final Iterator<T> mIterator;
    private final ToLongFunction<? super T> mMapper;

    public MapToLongIterator(Iterator<T> iterator, ToLongFunction<? super T> mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        final T item = mIterator.next();
        return mMapper.apply(item);
    }
}
