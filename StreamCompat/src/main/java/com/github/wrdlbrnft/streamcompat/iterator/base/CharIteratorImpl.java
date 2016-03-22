package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class CharIteratorImpl extends BaseCharIterator {

    private final CharIterator mIterator;

    public CharIteratorImpl(CharIterator iterator) {
        mIterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }
}
