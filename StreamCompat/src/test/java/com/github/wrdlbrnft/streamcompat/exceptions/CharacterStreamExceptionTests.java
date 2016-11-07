package com.github.wrdlbrnft.streamcompat.exceptions;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 01/11/2016
 */

public class CharacterStreamExceptionTests {

    @Test(expected = IllegalStateException.class)
    public void testRethrow() {
        final char[] actual = StreamCompat.of('1', null, '2')
                .mapToChar(Character::charValue)
                .exception(NullPointerException.class).rethrow(IllegalStateException::new)
                .toArray();

        Assert.fail("Should not reach this point.");
    }

    @Test
    public void testMapException() {
        final char[] actual = StreamCompat.of('1', null, '2')
                .mapToChar(Character::charValue)
                .exception(NullPointerException.class).mapException(e -> ' ')
                .toArray();

        final char[] expected = new char[]{'1', ' ', '2'};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testIgnoreException() {
        final char[] actual = StreamCompat.of('1', null, '2')
                .mapToChar(Character::charValue)
                .exception(NullPointerException.class).ignore()
                .toArray();

        final char[] expected = new char[]{'1', '2'};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testUnhandledException() {
        final char[] actual = StreamCompat.of('1', null, '2')
                .mapToChar(Character::charValue)
                .toArray();

        Assert.fail("Should not reach this point.");
    }
}
