package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class MapToIntIterator<T> extends BaseIntIterator {

    private final Iterator<T> mIterator;
    private final ToIntFunction<? super T> mMapper;

    public MapToIntIterator(Iterator<T> iterator, ToIntFunction<? super T> mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final T item = mIterator.next();
        return mMapper.apply(item);
    }
}
