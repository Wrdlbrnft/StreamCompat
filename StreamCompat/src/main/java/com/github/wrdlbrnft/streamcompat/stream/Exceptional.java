package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.Function;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 06/11/2016
 */

public interface Exceptional<T, E extends Throwable> extends BaseExceptional<Stream<T>, E> {
    Stream<T> mapException(Function<E, T> mapper);
}
