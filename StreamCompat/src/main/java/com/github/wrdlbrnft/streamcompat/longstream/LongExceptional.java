package com.github.wrdlbrnft.streamcompat.longstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.LongSupplier;
import com.github.wrdlbrnft.streamcompat.function.ToIntFunction;
import com.github.wrdlbrnft.streamcompat.function.ToLongFunction;
import com.github.wrdlbrnft.streamcompat.intstream.IntStream;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface LongExceptional<E extends Throwable> extends BaseExceptional<LongStream, E> {
    LongStream mapException(ToLongFunction<E> mapper);
}
