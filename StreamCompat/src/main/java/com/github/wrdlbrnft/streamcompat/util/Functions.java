package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.Function;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 17/08/16
 */

public class Functions {

    public static <T> Function<T, T> indentity() {
        return i -> i;
    }
}
