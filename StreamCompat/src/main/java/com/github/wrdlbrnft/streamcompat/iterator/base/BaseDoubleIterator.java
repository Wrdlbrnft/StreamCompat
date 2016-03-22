package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.iterator.DoubleIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseDoubleIterator extends BaseIterator<Double> implements DoubleIterator {

    @Override
    public final Double next() {
        return nextDouble();
    }


}
