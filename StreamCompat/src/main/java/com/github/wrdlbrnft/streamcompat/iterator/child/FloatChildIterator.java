package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;

import java.util.Iterator;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
public class FloatChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Float, C> implements FloatIterator {

    private final Predicate<C> mChildHasNext;
    private final ToFloatFunction<C> mChildGetNext;

    public FloatChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToFloatFunction<C> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final float nextFloat() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final Float next() {
        return nextFloat();
    }
}
