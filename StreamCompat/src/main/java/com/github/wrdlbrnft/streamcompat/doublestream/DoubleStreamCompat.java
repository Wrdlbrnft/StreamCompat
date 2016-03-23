package com.github.wrdlbrnft.streamcompat.doublestream;

import com.github.wrdlbrnft.streamcompat.iterator.array.ArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.array.DoubleArrayIterator;
import com.github.wrdlbrnft.streamcompat.iterator.primtive.DoubleIterator;
import com.github.wrdlbrnft.streamcompat.iterator.child.DoubleChildIterator;
import com.github.wrdlbrnft.streamcompat.util.EmptyIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 21/03/16.
 */
public class DoubleStreamCompat {

    static final DoubleIterator EMPTY_ITERATOR = new EmptyDoubleIterator();

    private static final DoubleStreamImpl EMPTY_STREAM = new DoubleStreamImpl(EMPTY_ITERATOR);

    public static DoubleStream empty() {
        return EMPTY_STREAM;
    }

    public static DoubleStream concat(DoubleStream... streams) {
        final Iterator<DoubleStream> iterator = new ArrayIterator<>(streams);
        final DoubleIterator[] buffer = new DoubleIterator[1];
        return new DoubleStreamImpl(new DoubleChildIterator<>(
                () -> {
                    if (buffer[0] == null || !buffer[0].hasNext()) {
                        if (!iterator.hasNext()) {
                            return EMPTY_ITERATOR;
                        }
                        buffer[0] = iterator.next().iterator();
                    }
                    return buffer[0];
                },
                DoubleIterator::hasNext,
                DoubleIterator::nextDouble
        ));
    }

    public static DoubleStream of(double... values) {
        final DoubleIterator iterator = new DoubleArrayIterator(values);
        return new DoubleStreamImpl(iterator);
    }

    public static DoubleStream of(DoubleIterator iterator) {
        return new DoubleStreamImpl(iterator);
    }

    private static class EmptyDoubleIterator extends EmptyIterator<Double> implements DoubleIterator {

        @Override
        public double nextDouble() {
            throw new NoSuchElementException();
        }
    }
}
