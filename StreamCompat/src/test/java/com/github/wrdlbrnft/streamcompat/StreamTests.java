package com.github.wrdlbrnft.streamcompat;

import android.support.v4.util.LongSparseArray;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStreamCompat;
import com.github.wrdlbrnft.streamcompat.stream.Collectors;
import com.github.wrdlbrnft.streamcompat.stream.StreamCompat;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by kapeller on 21/03/16.
 */
public class StreamTests {

    @Test
    public void testBasic() {
        final LongSparseArray<Long> array = StreamCompat.of("ASDF", "test", "wwwew", "z", "qqq", "sdalgekasdgkl", "q[rgeeu0", "zflb;ndz")
                .flatMapToChar(text -> CharacterStreamCompat.of(text.toCharArray())
                        .filter(Character::isLowerCase)
                        .limit(5))
                .boxed()
                .collect(Collectors.groupingInLongSparseArray(Character::hashCode, Collectors.toCount()));

        Assert.assertEquals(1L, array.get('a').longValue());
    }
}
