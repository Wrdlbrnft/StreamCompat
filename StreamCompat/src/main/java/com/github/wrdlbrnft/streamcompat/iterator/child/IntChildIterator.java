package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.IntIterator;

import java.util.Iterator;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
public class IntChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Integer, C> implements IntIterator {

    private final Predicate<C> mChildHasNext;
    private final ToIntFunction<C> mChildGetNext;

    public IntChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToIntFunction<C> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final int nextInt() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final Integer next() {
        return nextInt();
    }
}
