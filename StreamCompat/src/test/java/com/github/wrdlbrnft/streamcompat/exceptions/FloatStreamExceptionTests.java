package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class FloatStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        StreamCompat.of("Test")
                .mapToFloat(Float::parseFloat)
                .exception(NumberFormatException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final float[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToFloat(Float::parseFloat)
                .exception(NumberFormatException.class).mapException(e -> 27.0f)
                .toArray();

        final float[] expected = new float[]{1.2f, 0.0f, 27.0f, 27.0f, 900.0f, 432.123f, 27.0f};
        Assert.assertArrayEquals(expected, actual, 0.001f);
    }

    @Test
    public void testIgnoreException() {
        final float[] actual = StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToFloat(Float::parseFloat)
                .exception(NumberFormatException.class).ignore()
                .toArray();

        final float[] expected = new float[]{1.2f, 0.0f, 900.0f, 432.123f};
        Assert.assertArrayEquals(expected, actual, 0.001f);
    }

    @Test(expected = NumberFormatException.class)
    public void testUnhandledException() {
        StreamCompat.of("1.2", "0.0", "A", "Test", "900", "432.123", "Z")
                .mapToFloat(Float::parseFloat)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
