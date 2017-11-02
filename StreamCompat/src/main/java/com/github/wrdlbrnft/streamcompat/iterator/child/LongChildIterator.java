package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.LongIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;

import java.util.Iterator;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
public class LongChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Long, C> implements LongIterator {

    private final Predicate<C> mChildHasNext;
    private final ToLongFunction<C> mChildGetNext;

    public LongChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToLongFunction<C> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final long nextLong() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final Long next() {
        return nextLong();
    }
}
