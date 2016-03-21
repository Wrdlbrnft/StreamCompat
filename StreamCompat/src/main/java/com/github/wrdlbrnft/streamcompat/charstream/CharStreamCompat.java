package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.iterator.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;

/**
 * Created by kapeller on 21/03/16.
 */
public class CharStreamCompat {

    public static CharStream of(char... values) {
        final CharIterator iterator = new CharArrayIterator(values);
        return new CharStreamImpl(iterator);
    }

    public static CharStream of(CharIterator iterator) {
        return new CharStreamImpl(iterator);
    }
}
