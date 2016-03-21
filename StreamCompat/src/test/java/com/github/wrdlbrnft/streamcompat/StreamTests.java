package com.github.wrdlbrnft.streamcompat;

import com.github.wrdlbrnft.streamcompat.longstream.LongStreamCompat;

import org.junit.Test;

/**
 * Created by kapeller on 21/03/16.
 */
public class StreamTests {

    @Test
    public void testBasicIntStream() {
        final long result = LongStreamCompat.range(1, 1000000000)
                .map(i -> i / 10000000L)
                .sum();

        System.out.println(result);
    }
}
