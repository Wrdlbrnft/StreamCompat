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
    public void testBasicIntStream() {
        final long result = StreamCompat.of("ASDF", "test", "wwwew", "z", "qqq", "sdalgekasdgkl", "q[rgeeu0", "zflb;ndz")
                .flatMapToChar(text -> CharacterStreamCompat.of(text.toCharArray()))
                .filter(c -> c == 'e')
                .count();

        Assert.assertEquals(5, result);
    }
}
