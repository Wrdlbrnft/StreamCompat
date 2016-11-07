package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.ToDoubleFunction;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface DoubleExceptional<E extends Throwable> extends BaseExceptional<DoubleStream, E> {
    DoubleStream mapException(ToDoubleFunction<E> mapper);
}
