package com.github.wrdlbrnft.streamcompat.exceptional;

import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.stream.Stream;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface BaseExceptional<S, E extends Throwable> {
    S consume(Consumer<E> consumer);
    <I extends RuntimeException> S rethrow(Function<E, I> mapper);
    S ignore();
}
