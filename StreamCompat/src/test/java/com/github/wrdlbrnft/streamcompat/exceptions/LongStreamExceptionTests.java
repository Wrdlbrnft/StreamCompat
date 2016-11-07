package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class LongStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        StreamCompat.of("Test")
                .mapToLong(Long::parseLong)
                .exception(NumberFormatException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final long[] actual = StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToLong(Long::parseLong)
                .exception(NumberFormatException.class).mapException(e -> 27)
                .toArray();

        final long[] expected = new long[]{1, 0, 27, 27, 900, 432, 27};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIgnoreException() {
        final long[] actual = StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToLong(Long::parseLong)
                .exception(NumberFormatException.class).ignore()
                .toArray();

        final long[] expected = new long[]{1, 0, 900, 432};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testUnhandledException() {
        StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToLong(Long::parseLong)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
