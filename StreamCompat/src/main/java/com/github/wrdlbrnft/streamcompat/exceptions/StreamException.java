package com.github.wrdlbrnft.streamcompat.exceptions;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class StreamException extends RuntimeException {

    public StreamException(String message) {
        super(message);
    }

    public StreamException(String message, Throwable cause) {
        super(message, cause);
    }
}
