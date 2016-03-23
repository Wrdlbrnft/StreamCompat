package com.github.wrdlbrnft.streamcompat.iterator.base;

import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 22/03/16.
 */
public abstract class BaseChildIterator<I, O, C extends Iterator<I>> extends BaseIterator<O> {

    private final Supplier<C> mSupplier;
    private C mChild;

    public BaseChildIterator(Supplier<C> supplier) {
        mSupplier = supplier;
    }

    protected final C child() {
        if (mChild == null || !mChild.hasNext()) {
            mChild = mSupplier.get();
        }

        return mChild;
    }
}
