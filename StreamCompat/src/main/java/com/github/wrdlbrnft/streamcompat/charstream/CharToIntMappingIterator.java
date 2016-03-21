package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.function.CharToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharToIntMappingIterator extends BaseIntIterator {

    private final CharIterator mIterator;
    private final CharToIntFunction mMapper;

    CharToIntMappingIterator(CharIterator iterator, CharToIntFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public int nextInt() {
        final char value = mIterator.nextChar();
        return mMapper.applyAsInt(value);
    }
}
