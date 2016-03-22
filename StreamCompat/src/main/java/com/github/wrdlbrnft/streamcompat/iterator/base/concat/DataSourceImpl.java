package com.github.wrdlbrnft.streamcompat.iterator.base.concat;

import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;

/**
 * Created by kapeller on 22/03/16.
 */
class DataSourceImpl<T> implements DataSource<T> {

    private final IntFunction<T> mSupplier;
    private final IntPredicate mPredicate;

    DataSourceImpl(IntFunction<T> supplier, IntPredicate predicate) {
        mSupplier = supplier;
        mPredicate = predicate;
    }

    @Override
    public IntFunction<T> supplier() {
        return mSupplier;
    }

    @Override
    public IntPredicate predicate() {
        return mPredicate;
    }
}
