package com.github.wrdlbrnft.streamcompat.floatstream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.FloatArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.FloatIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.FloatChildIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
public class FloatStreamCompat {

    static final FloatIterator EMPTY_ITERATOR = new EmptyFloatIterator();

    private static final FloatStreamImpl EMPTY_STREAM = new FloatStreamImpl(EMPTY_ITERATOR);

    public static FloatStream empty() {
        return EMPTY_STREAM;
    }

    public static FloatStream concat(FloatStream... streams) {
        final Iterator<FloatStream> iterator = new ArrayIterator<>(streams);
        final FloatIterator[] buffer = new FloatIterator[1];
        return new FloatStreamImpl(new FloatChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                FloatIterator::hasNext,
                FloatIterator::nextFloat
        ));
    }

    public static FloatStream of(float... values) {
        final FloatIterator iterator = new FloatArrayIterator(values);
        return new FloatStreamImpl(iterator);
    }

    public static FloatStream of(FloatIterator iterator) {
        return new FloatStreamImpl(iterator);
    }

    private static class EmptyFloatIterator extends EmptyIterator<Float> implements FloatIterator {

        @Override
        public float nextFloat() {
            throw new NoSuchElementException();
        }
    }
}
