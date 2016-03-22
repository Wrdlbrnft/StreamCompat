package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.CharConsumer;
import com.github.wrdlbrnft.streamcompat.function.CharSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;

import java.util.NoSuchElementException;

public class OptionalCharacter {

    private static final OptionalCharacter EMPTY = new OptionalCharacter();
    
    private final boolean mIsPresent;
    private final char mValue;

    private OptionalCharacter() {
        mIsPresent = false;
        mValue = 0;
    }
    
    public static OptionalCharacter empty() {
        return EMPTY;
    }
    
    private OptionalCharacter(char value) {
        mIsPresent = true;
        mValue = value;
    }
    
    public static OptionalCharacter of(char value) {
        return new OptionalCharacter(value);
    }
    
    public char getAsCharacter() {
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

        if (!(obj instanceof OptionalCharacter)) {
            return false;
        }

        OptionalCharacter other = (OptionalCharacter) obj;
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