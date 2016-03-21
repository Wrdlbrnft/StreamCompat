package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.IntIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseIntIterator extends BaseIterator<Integer> implements IntIterator {

    @Override
    public final Integer next() {
        return nextInt();
    }
}
