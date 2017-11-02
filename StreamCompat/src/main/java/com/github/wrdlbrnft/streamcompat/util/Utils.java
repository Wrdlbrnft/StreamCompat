package com.github.wrdlbrnft.streamcompat.util;

import java.util.Iterator;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 10/03/16
 */
public class Utils {

    private static final Iterator<?> EMPTY_ITERATOR = new EmptyIterator<>();

    public static <T> T requireNonNull(T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

    public static <T> T requireNonNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    public static <A, B> boolean equal(A a, B b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static <T> int hashCode(T object) {
        return (object == null) ? 0 : object.hashCode();
    }

    public static int hashCode(int value) {
        return value;
    }

    public static int hashCode(char value) {
        return value;
    }

    public static int hashCode(long value) {
        return (int) (value ^ (value >>> 32));
    }

    public static int hashCode(double value) {
        final long v = Double.doubleToLongBits(value);
        return (int) (v ^ (v >>> 32));
    }

    public static int hashCode(float value) {
        return Float.floatToIntBits(value);
    }

    @SuppressWarnings("unchecked")
    public static <T> Iterator<T> emptyIterator() {
        return (Iterator<T>) EMPTY_ITERATOR;
    }

}
