package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseCharIterator extends BaseIterator<Character> implements CharIterator {

    @Override
    public final Character next() {
        return nextChar();
    }
}
