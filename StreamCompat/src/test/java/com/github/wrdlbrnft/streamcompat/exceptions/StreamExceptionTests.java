package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class StreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        StreamCompat.of("Test")
                .map(Double::parseDouble)
                .exception(NumberFormatException.class).rethrow(IllegalStateException::new)
                .toArray(Double[]::new);

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final Double[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .map(Double::parseDouble)
                .exception(NumberFormatException.class).mapException(e -> 0.0)
                .toArray(Double[]::new);

        final Double[] expected = new Double[]{1.2, 0.0, 0.0, 0.0, 900.0, 432.123, 0.0};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIgnoreException() {
        final Double[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .map(Double::parseDouble)
                .exception(NumberFormatException.class).ignore()
                .toArray(Double[]::new);

        final Double[] expected = new Double[]{1.2, 0.0, 900.0, 432.123};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = NumberFormatException.class)
    public void testUnhandledException() {
        StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .map(Double::parseDouble)
                .toArray(Double[]::new);

        Assert.fail("Should not reach this point.");
    }
}
