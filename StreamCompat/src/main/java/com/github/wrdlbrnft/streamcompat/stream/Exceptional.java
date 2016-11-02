package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface Exceptional<T, E extends Throwable> {
    Stream<T> consume(Consumer<E> consumer);
    <I extends RuntimeException> Stream<T> rethrow(Function<E, I> mapper);
    Stream<T> mapException(Function<E, T> mapper);
    Stream<T> ignore();
}
