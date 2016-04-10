package com.github.wrdlbrnft.streamcompat.iterator.child;

import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.function.ToByteFunction;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;

import java.util.Iterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class ByteChildIterator<I, C extends Iterator<I>> extends BaseChildIterator<I, Byte, C> implements ByteIterator {

    private final Predicate<C> mChildHasNext;
    private final ToByteFunction<C> mChildGetNext;

    public ByteChildIterator(Supplier<C> supplier, Predicate<C> childHasNext, ToByteFunction<C> childGetNext) {
        super(supplier);
        mChildHasNext = childHasNext;
        mChildGetNext = childGetNext;
    }

    @Override
    public final byte nextByte() {
        final C child = child();
        return mChildGetNext.apply(child);
    }

    @Override
    public final boolean hasNext() {
        final C child = child();
        return mChildHasNext.test(child);
    }

    @Override
    public final Byte next() {
        return nextByte();
    }
}
