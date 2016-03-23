package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 22/03/16.
 */
public class ChildIterator<I, O, C extends Iterator<I>> extends BaseChildIterator<I, O, C> {

    private final Predicate<C> mChildHasNext;
    private final Function<C, O> mChildGetNext;

    public ChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, Function<C, O> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final O next() {
        final C child = child();
        return mChildGetNext.apply(child);
    }
}
