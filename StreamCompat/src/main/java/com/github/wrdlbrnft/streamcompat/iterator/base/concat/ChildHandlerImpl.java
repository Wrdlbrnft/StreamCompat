package com.github.wrdlbrnft.streamcompat.iterator.base.concat;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;

/**
 * Created by kapeller on 22/03/16.
 */
class ChildHandlerImpl<C, N> implements ChildHandler<C, N> {

    private final Predicate<C> mPredicate;
    private final Function<C, N> mNext;

    ChildHandlerImpl(Predicate<C> predicate, Function<C, N> next) {
        mPredicate = predicate;
        mNext = next;
    }

    @Override
    public Predicate<C> hasNext() {
        return mPredicate;
    }

    @Override
    public Function<C, N> next() {
        return mNext;
    }
}
