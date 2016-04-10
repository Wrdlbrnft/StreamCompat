package com.github.wrdlbrnft.streamcompat.bytestream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.ByteArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.ByteChildIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.ByteIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
public class ByteStreamCompat {

    static final ByteIterator EMPTY_ITERATOR = new EmptyByteIterator();

    private static final ByteStream EMPTY_STREAM = new ByteStreamImpl(EMPTY_ITERATOR);

    public static ByteStream empty() {
        return EMPTY_STREAM;
    }

    public static ByteStream concat(ByteStream... streams) {
        final Iterator<ByteStream> iterator = new ArrayIterator<>(streams);
        final ByteIterator[] buffer = new ByteIterator[1];
        return new ByteStreamImpl(new ByteChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                ByteIterator::hasNext,
                ByteIterator::nextByte
        ));
    }

    public static ByteStream of(byte... values) {
        final ByteIterator iterator = new ByteArrayIterator(values);
        return new ByteStreamImpl(iterator);
    }

    public static ByteStream of(ByteIterator iterator) {
        return new ByteStreamImpl(iterator);
    }

    private static class EmptyByteIterator extends EmptyIterator<Byte> implements ByteIterator {

        @Override
        public byte nextByte() {
            throw new NoSuchElementException();
        }
    }
}
