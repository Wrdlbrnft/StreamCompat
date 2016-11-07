package com.github.wrdlbrnft.streamcompat.intstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStream;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface IntExceptional<E extends Throwable> extends BaseExceptional<IntStream, E> {
    IntStream mapException(ToIntFunction<E> mapper);
}
