package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.exceptional.BaseExceptional;
import com.github.wrdlbrnft.streamcompat.function.ToCharFunction;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 31/10/2016
 */

public interface CharacterExceptional<E extends Throwable> extends BaseExceptional<CharacterStream, E> {
    CharacterStream mapException(ToCharFunction<E> mapper);
}
