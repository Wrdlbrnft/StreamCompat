package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.FloatIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseFloatIterator extends BaseIterator<Float> implements FloatIterator {

    @Override
    public final Float next() {
        return nextFloat();
    }
}
