package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.BiConsumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Supplier;

/**
 * Created by kapeller on 10/03/16.
 */
class CollectorImpl<T, A, R> implements Collector<T, A, R> {

    private final Supplier<A> mSupplier;
    private final BiConsumer<A, T> mAccumulator;
    private final Function<A, R> mFinisher;

    public CollectorImpl(Supplier<A> supplier, BiConsumer<A, T> accumulator, Function<A, R> finisher) {
        mSupplier = supplier;
        mAccumulator = accumulator;
        mFinisher = finisher;
    }

    @Override
    public Supplier<A> supplier() {
        return mSupplier;
    }

    @Override
    public BiConsumer<A, T> accumulator() {
        return mAccumulator;
    }

    @Override
    public Function<A, R> finisher() {
        return mFinisher;
    }
}
