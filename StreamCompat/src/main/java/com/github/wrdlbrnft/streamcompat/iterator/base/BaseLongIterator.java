package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.LongIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseLongIterator extends BaseIterator<Long> implements LongIterator {

    @Override
    public final Long next() {
        return nextLong();
    }
}
