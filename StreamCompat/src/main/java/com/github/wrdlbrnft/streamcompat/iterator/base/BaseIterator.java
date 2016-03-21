package com.github.wrdlbrnft.streamcompat.iterator.base;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
public abstract class BaseIterator<O> implements Iterator<O> {

    @Override
    public final void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
