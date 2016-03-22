package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.function.CharToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseDoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharacterToDoubleMappingIterator extends BaseDoubleIterator {

    private final CharIterator mIterator;
    private final CharToDoubleFunction mMapper;

    CharacterToDoubleMappingIterator(CharIterator iterator, CharToDoubleFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public double nextDouble() {
        final char value = mIterator.nextChar();
        return mMapper.applyAsDouble(value);
    }
}
