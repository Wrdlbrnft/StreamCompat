package com.github.wrdlbrnft.streamcompat.iterator.base.concat;

import com.github.wrdlbrnft.streamcompat.function.IntFunction;
import com.github.wrdlbrnft.streamcompat.function.IntPredicate;

import java.util.Iterator;

/**
 * Created by kapeller on 22/03/16.
 */
public interface DataSource<T> {
    IntFunction<T> supplier();
    IntPredicate predicate();

    static <T> DataSource<T> of(Iterator<T> iterator) {
        return new DataSourceImpl<>(
                i -> iterator.next(),
                i -> iterator.hasNext()
        );
    }

    static <T> DataSource<T> of(T[] array) {
        return new DataSourceImpl<>(
                i -> array[i],
                i -> i < array.length
        );
    }
}
