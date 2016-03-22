package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.function.CharUnaryOperator;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 10/03/16.
 */
class CharacterMappingIterator extends BaseCharIterator implements CharIterator {

    private final CharIterator mBaseIterator;
    private final CharUnaryOperator mMapper;

    public CharacterMappingIterator(CharIterator iterator, CharUnaryOperator mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mBaseIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final char next = mBaseIterator.nextChar();
        return mMapper.applyAsChar(next);
    }
}
