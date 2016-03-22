package com.github.wrdlbrnft.streamcompat.iterator.base.concat;

import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;

import java.util.Iterator;

/**
 * Created by kapeller on 22/03/16.
 */
public interface ChildHandler<C, N> {
    Predicate<C> hasNext();
    Function<C, N> next();

    static <C extends Iterator<? extends N>, N> ChildHandler<C, N> forIterator() {
        return new ChildHandlerImpl<>(
                Iterator::hasNext,
                Iterator::next
        );
    }

    static <C extends Iterator<? extends N>, N> ChildHandler<C, N> of(Predicate<C> predicate, Function<C, N> next) {
        return new ChildHandlerImpl<>(predicate, next);
    }
}
