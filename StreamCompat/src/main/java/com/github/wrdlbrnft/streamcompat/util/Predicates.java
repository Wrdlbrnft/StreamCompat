package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.Predicate;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 10/03/16
 */
public class Predicates {

    public static <T> Predicate<T> notNull() {
        return o -> o != null;
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return i -> !predicate.test(i);
    }
}
