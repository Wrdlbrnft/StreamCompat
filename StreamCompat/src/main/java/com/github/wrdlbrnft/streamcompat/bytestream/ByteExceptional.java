package com.github.wrdlbrnft.streamcompat.bytestream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.ToByteFunction;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface ByteExceptional<E extends Throwable> extends BaseExceptional<ByteStream, E> {
    ByteStream mapException(ToByteFunction<E> mapper);
}
