package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.iterator.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.CharIteratorImpl;

/**
 * Created by kapeller on 21/03/16.
 */
public class CharacterStreamCompat {

    public static CharacterStream of(char... values) {
        final CharIterator iterator = new CharArrayIterator(values);
        return new CharacterStreamImpl(iterator);
    }

    public static CharacterStream of(CharIterator iterator) {
        return new CharacterStreamImpl(iterator);
    }
}
