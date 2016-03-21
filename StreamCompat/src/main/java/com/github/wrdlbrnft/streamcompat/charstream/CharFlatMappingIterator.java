package com.github.wrdlbrnft.streamcompat.charstream;

import com.github.wrdlbrnft.streamcompat.function.CharFunction;
import com.github.wrdlbrnft.streamcompat.iterator.CharIterator;
import com.github.wrdlbrnft.streamcompat.iterator.base.BaseCharIterator;

import java.util.NoSuchElementException;

/**
 * Created by kapeller on 10/03/16.
 */
class CharFlatMappingIterator extends BaseCharIterator implements CharIterator {

    private final CharIterator mBaseIterator;
    private final CharFunction<? extends CharStream> mMapper;

    private CharIterator mChild = null;
    private boolean mHasNext;
    private char mNext;

    public CharFlatMappingIterator(CharIterator iterator, CharFunction<? extends CharStream> mapper) {
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
            final char input = mBaseIterator.nextChar();
            final CharStream stream = mMapper.apply(input);
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
