package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class DoubleStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        StreamCompat.of("Test")
                .mapToDouble(Double::parseDouble)
                .exception(NumberFormatException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final double[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToDouble(Double::parseDouble)
                .exception(NumberFormatException.class).mapException(e -> 0.0)
                .toArray();

        final double[] expected = new double[]{1.2, 0.0, 0.0, 0.0, 900.0, 432.123, 0.0};
        Assert.assertArrayEquals(expected, actual, 0.001);
    }

    @Test
    public void testIgnoreException() {
        final double[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToDouble(Double::parseDouble)
                .exception(NumberFormatException.class).ignore()
                .toArray();

        final double[] expected = new double[]{1.2, 0.0, 900.0, 432.123};
        Assert.assertArrayEquals(expected, actual, 0.001);
    }

    @Test(expected = NumberFormatException.class)
    public void testUnhandledException() {
        StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToDouble(Double::parseDouble)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
