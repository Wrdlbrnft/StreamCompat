package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.ToFloatFunction;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface FloatExceptional<E extends Throwable> extends BaseExceptional<FloatStream, E> {
    FloatStream mapException(ToFloatFunction<E> mapper);
}
