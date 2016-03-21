package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
class MapToCharIterator<T> extends BaseCharIterator {

    private final Iterator<T> mIterator;
    private final ToCharFunction<? super T> mMapper;

    public MapToCharIterator(Iterator<T> iterator, ToCharFunction<? super T> mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final T item = mIterator.next();
        return mMapper.apply(item);
    }
}
