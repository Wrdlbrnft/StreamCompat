package com.github.wrdlbrnft.streamcompat.characterstream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.CharArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.CharChildIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with Android Studio<br>
 * User: kapeller<br>
 * Date: 21/03/16
 */
public class CharacterStreamCompat {

    static final CharIterator EMPTY_ITERATOR = new EmptyCharIterator();

    private static final CharacterStream EMPTY_STREAM = new CharacterStreamImpl(EMPTY_ITERATOR);

    public static CharacterStream empty() {
        return EMPTY_STREAM;
    }

    public static CharacterStream concat(CharacterStream... streams) {
        final Iterator<CharacterStream> iterator = new ArrayIterator<>(streams);
        final CharIterator[] buffer = new CharIterator[1];
        return new CharacterStreamImpl(new CharChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                CharIterator::hasNext,
                CharIterator::nextChar
        ));
    }

    public static CharacterStream of(char... values) {
        final CharIterator iterator = new CharArrayIterator(values);
        return new CharacterStreamImpl(iterator);
    }

    public static CharacterStream of(CharIterator iterator) {
        return new CharacterStreamImpl(iterator);
    }

    private static class EmptyCharIterator extends EmptyIterator<Character> implements CharIterator {

        @Override
        public char nextChar() {
            throw new NoSuchElementException();
        }
    }
}
