package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.function.CharToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseFloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharacterToFloatMappingIterator extends BaseFloatIterator {

    private final CharIterator mIterator;
    private final CharToFloatFunction mMapper;

    CharacterToFloatMappingIterator(CharIterator iterator, CharToFloatFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public float nextFloat() {
        final char value = mIterator.nextChar();
        return mMapper.applyAsFloat(value);
    }
}
