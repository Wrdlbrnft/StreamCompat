package com.github.wrdlbrnft.streamcompat.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 23/03/16
 */
public class EmptyIterator<T> implements Iterator<T> {

    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public T next() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        throw new IllegalStateException();
    }
}
