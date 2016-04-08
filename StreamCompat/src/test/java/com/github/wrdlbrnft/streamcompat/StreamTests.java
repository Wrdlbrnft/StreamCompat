package com.github.wrdlbrnft.streamcompat;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kapeller on 21/03/16.
 */
public class StreamTests {

    @Test
    public void testCharacterStreamToArray() {
        final char[] actual = StreamCompat.of("Android", " ", "Google")
                .flatMapToChar(text -> CharacterStreamCompat.of(text.toCharArray()))
                .toArray();

        final char[] expected = new char[]{'A', 'n', 'd', 'r', 'o', 'i', 'd', ' ', 'G', 'o', 'o', 'g', 'l', 'e'};
        Assert.assertArrayEquals(expected, actual);
    }

    @Test
    public void testDistinct() {
        final String[] actual = StreamCompat.of("Android", "Android", " ", "Apple", "Google", "Google")
                .distinct()
                .toArray(String[]::new);

        final String[] expected = new String[]{"Android", " ", "Apple", "Google"};
        Assert.assertArrayEquals(expected, actual);
    }
}
