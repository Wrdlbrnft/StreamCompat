package com.github.wrdlbrnft.streamcompat.iterator.base.concat;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseIterator;
import com.github.wrdlbrnft.streamcompat.stream.Stream;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.Iterator;

/**
 * Created by kapeller on 22/03/16.
 */
public class BaseConcatIterator<I, O, C> extends BaseIterator<O> {

    private final IntFunction<I> mSupplier;
    private final IntPredicate mContinuePredicate;
    private final Function<I, C> mMapper;
    private final Predicate<C> mChildHasNext;
    private final Function<C, O> mChildGetNext;
    private final Supplier<C> mEmptySupplier;

    private int mIndex = 0;
    private C mChild;

    public BaseConcatIterator(DataSource<I> source, Function<I, C> mapper, ChildHandler<C, O> handler, Supplier<C> emptySupplier) {
        mSupplier = source.supplier();
        mContinuePredicate = source.predicate();
        mMapper = mapper;
        mChildHasNext = handler.hasNext();
        mChildGetNext = handler.next();
        mEmptySupplier = emptySupplier;
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

    private C child() {
        if (mChild == null || !mChildHasNext.test(mChild)) {
            if (!mContinuePredicate.test(mIndex)) {
                return mEmptySupplier.get();
            }

            mChild = mMapper.apply(mSupplier.apply(mIndex++));
        }

        return mChild;
    }
}
