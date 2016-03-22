package com.github.wrdlbrnft.streamcompat.stream;

import com.github.wrdlbrnft.streamcompat.characterstream.CharacterStream;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class FlatMapToCharIterator<I> extends BaseCharIterator {

    private final Iterator<I> mBaseIterator;
    private final Function<? super I, ? extends CharacterStream> mMapper;

    private CharIterator mChild = null;
    private boolean mHasNext;
    private char mNext;

    public FlatMapToCharIterator(Iterator<I> iterator, Function<? super I, ? extends CharacterStream> mapper) {
        mBaseIterator = iterator;
        mMapper = mapper;
        moveToNext();
    }

    private void moveToNext() {
        if (mChild != null && mChild.hasNext()) {
            mHasNext = true;
            mNext = mChild.nextChar();
            return;
        }

        mChild = null;

        while (mBaseIterator.hasNext()) {
            final I input = mBaseIterator.next();
            final CharacterStream stream = mMapper.apply(input);
            mChild = stream.iterator();
            if (mChild != null && mChild.hasNext()) {
                mHasNext = true;
                mNext = mChild.nextChar();
                return;
            }
        }
        mHasNext = false;
    }

    @Override
    public boolean hasNext() {
        return mHasNext;
    }

    @Override
    public char nextChar() {
        if(!mHasNext) {
            throw new NoSuchElementException("No items left to iterate over.");
        }
        final char current = mNext;
        moveToNext();
        return current;
    }
}
