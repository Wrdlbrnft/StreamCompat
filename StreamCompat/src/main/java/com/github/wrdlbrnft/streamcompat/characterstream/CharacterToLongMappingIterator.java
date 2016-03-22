package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.function.CharToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseLongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class CharacterToLongMappingIterator extends BaseLongIterator {

    private final CharIterator mIterator;
    private final CharToLongFunction mMapper;

    CharacterToLongMappingIterator(CharIterator iterator, CharToLongFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public long nextLong() {
        final char value = mIterator.nextChar();
        return mMapper.applyAsLong(value);
    }
}
