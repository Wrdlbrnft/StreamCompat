package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;

import java.util.Iterator;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
public class DoubleChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Double, C> implements DoubleIterator {

    private final Predicate<C> mChildHasNext;
    private final ToDoubleFunction<C> mChildGetNext;

    public DoubleChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToDoubleFunction<C> childGetNext) {
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
    public final double nextDouble() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final Double next() {
        return nextDouble();
    }
}
