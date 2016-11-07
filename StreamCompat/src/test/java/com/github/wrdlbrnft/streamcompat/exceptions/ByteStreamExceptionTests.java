package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class ByteStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        final byte[] actual = StreamCompat.of(1, null, 2)
                .mapToByte(Integer::byteValue)
                .exception(NullPointerException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final byte[] actual = StreamCompat.of(1, null, 2)
                .mapToByte(Integer::byteValue)
                .exception(NullPointerException.class).mapException(e -> (byte) 0)
                .toArray();

        final byte[] expected = new byte[]{1, 0, 2};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIgnoreException() {
        final byte[] actual = StreamCompat.of(1, null, 2)
                .mapToByte(Integer::byteValue)
                .exception(NullPointerException.class).ignore()
                .toArray();

        final byte[] expected = new byte[]{1, 2};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testUnhandledException() {
        final byte[] actual = StreamCompat.of(1, null, 2)
                .mapToByte(Integer::byteValue)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
