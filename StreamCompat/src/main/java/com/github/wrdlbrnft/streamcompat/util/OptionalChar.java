package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.CharConsumer;
import com.github.wrdlbrnft.streamcompat.function.CharSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;

import java.util.NoSuchElementException;

public class OptionalChar {

    private static final OptionalChar EMPTY = new OptionalChar();
    
    private final boolean mIsPresent;
    private final char mValue;

    private OptionalChar() {
        mIsPresent = false;
        mValue = 0;
    }
    
    public static OptionalChar empty() {
        return EMPTY;
    }
    
    private OptionalChar(char value) {
        mIsPresent = true;
        mValue = value;
    }
    
    public static OptionalChar of(char value) {
        return new OptionalChar(value);
    }
    
    public char getAsChar() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }
    
    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(CharConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public char orElse(char other) {
        return mIsPresent ? mValue : other;
    }

    public char orElseGet(CharSupplier other) {
        return mIsPresent ? mValue : other.getAsChar();
    }

    public<X extends Throwable> char orElseThrow(Supplier<X> exceptionSupplier) throws X {
        if (mIsPresent) {
            return mValue;
        } else {
            throw exceptionSupplier.get();
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OptionalChar)) {
            return false;
        }

        OptionalChar other = (OptionalChar) obj;
        return (mIsPresent && other.mIsPresent)
                ? mValue == other.mValue
                : mIsPresent == other.mIsPresent;
    }

    @Override 
    public int hashCode() {
        return mIsPresent ? Utils.hashCode(mValue) : 0;
    }

    @Override
    public String toString() {
        return mIsPresent
                ? String.format("OptionalChar[%s]", mValue)
                : "OptionalChar.empty";
    }
}