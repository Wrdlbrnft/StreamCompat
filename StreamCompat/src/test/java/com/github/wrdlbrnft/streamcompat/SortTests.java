package com.github.wrdlbrnft.streamcompat;

import com.github.wrdlbrnft.streamcompat.bytestream.ByteStreamCompat;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.doublestream.DoubleStreamCompat;
import com.github.wrdlbrnft.streamcompat.floatstream.FloatStreamCompat;
import com.github.wrdlbrnft.streamcompat.intstream.IntStreamCompat;
import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 17/08/16
 */
public class SortTests {

    @Test
    public void testStreamSort() {
        final String[] actual = StreamCompat.of("Apple", "Android", "Google", "Android", "Google")
                .sort(String::compareTo)
                .toArray(String[]::new);

        final String[] expected = new String[]{"Android", "Android", "Apple", "Google", "Google"};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIntStreamSort() {
        final int[] actual = IntStreamCompat.of(7, 5, 2, 5, 4, 0, 9, 1, 13)
                .sort()
                .toArray();

        final int[] expected = new int[]{0, 1, 2, 4, 5, 5, 7, 9, 13};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testLongStreamSort() {
        final long[] actual = LongStreamCompat.of(7, 5, 2, 5, 4, 0, 9, 1, 13)
                .sort()
                .toArray();

        final long[] expected = new long[]{0, 1, 2, 4, 5, 5, 7, 9, 13};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testFloatStreamSort() {
        final float[] actual = FloatStreamCompat.of(7, 5, 2, 5, 4, 0, 9, 1, 13)
                .sort()
                .toArray();

        final float[] expected = new float[]{0, 1, 2, 4, 5, 5, 7, 9, 13};
        Assert.assertArrayEquals(expected, actual, 0.00000000001f);
    }

    @Test
    public void testDoubleStreamSort() {
        final double[] actual = DoubleStreamCompat.of(7, 5, 2, 5, 4, 0, 9, 1, 13)
                .sort()
                .toArray();

        final double[] expected = new double[]{0, 1, 2, 4, 5, 5, 7, 9, 13};
        Assert.assertArrayEquals(expected, actual, 0.00000000001f);
    }

    @Test
    public void testCharacterStreamSort() {
        final char[] actual = CharacterStreamCompat.of('h', 'f', 'c', 'f', 'e', 'a', 'j', 'b', 'n')
                .sort()
                .toArray();

        final char[] expected = new char[]{'a', 'b', 'c', 'e', 'f', 'f', 'h', 'j', 'n'};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testByteStreamSort() {
        final byte[] actual = ByteStreamCompat.of(new byte[]{7, 5, 2, 5, 4, 0, 9, 1, 13})
                .sort()
                .toArray();

        final byte[] expected = new byte[]{0, 1, 2, 4, 5, 5, 7, 9, 13};
        Assert.assertArrayEquals(expected, actual);
    }
}
