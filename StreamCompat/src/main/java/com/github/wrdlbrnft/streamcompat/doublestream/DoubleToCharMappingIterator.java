package com.github.wrdlbrnft.streamcompat.doublestream;

import android.database.CursorJoiner;

import com.github.wrdlbrnft.streamcompat.function.DoubleToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
class DoubleToCharMappingIterator extends BaseCharIterator {

    private final DoubleIterator mIterator;
    private final DoubleToCharFunction mMapper;

    DoubleToCharMappingIterator(DoubleIterator iterator, DoubleToCharFunction mapper) {
        mIterator = iterator;
        mMapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public char nextChar() {
        final double value = mIterator.nextDouble();
        return mMapper.applyAsChar(value);
    }
}
