package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class IntStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        StreamCompat.of("Test")
                .mapToInt(Integer::parseInt)
                .exception(NumberFormatException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final int[] actual = StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToInt(Integer::parseInt)
                .exception(NumberFormatException.class).mapException(e -> 27)
                .toArray();

        final int[] expected = new int[]{1, 0, 27, 27, 900, 432, 27};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIgnoreException() {
        final int[] actual = StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToInt(Integer::parseInt)
                .exception(NumberFormatException.class).ignore()
                .toArray();

        final int[] expected = new int[]{1, 0, 900, 432};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testUnhandledException() {
        StreamCompat.of("1", "0", "A", "Test", "900", "432", "Z")
                .mapToInt(Integer::parseInt)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
