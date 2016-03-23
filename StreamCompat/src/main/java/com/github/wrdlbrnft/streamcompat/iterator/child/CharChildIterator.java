package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class CharChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Character, C> implements CharIterator {

    private final Predicate<C> mChildHasNext;
    private final ToCharFunction<C> mChildGetNext;

    public CharChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToCharFunction<C> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final char nextChar() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final Character next() {
        return nextChar();
    }
}
