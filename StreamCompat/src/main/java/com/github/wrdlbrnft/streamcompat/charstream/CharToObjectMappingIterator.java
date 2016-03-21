package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 10/03/16.
 */
class CharToObjectMappingIterator<O> extends BaseIterator<O> implements Iterator<O> {

    private final CharIterator mBaseIterator;
    private final CharFunction<? extends O> mMapper;

    public CharToObjectMappingIterator(CharIterator iterator, CharFunction<? extends O> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public O next() {
        final char next = mBaseIterator.nextChar();
        return mMapper.apply(next);
    }
}
