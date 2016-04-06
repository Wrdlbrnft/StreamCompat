package com.github.wrdlbrnft.streamcompat.optionals;

import com.github.wrdlbrnft.streamcompat.function.IntConsumer;
import com.github.wrdlbrnft.streamcompat.function.IntSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.NoSuchElementException;

public class OptionalInt {

    private static final OptionalInt EMPTY = new OptionalInt();
    
    private final boolean mIsPresent;
    private final int mValue;

    private OptionalInt() {
        mIsPresent = false;
        mValue = 0;
    }
    
    public static OptionalInt empty() {
        return EMPTY;
    }
    
    private OptionalInt(int value) {
        mIsPresent = true;
        mValue = value;
    }
    
    public static OptionalInt of(int value) {
        return new OptionalInt(value);
    }
    
    public int getAsInt() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }
    
    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(IntConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public int orElse(int other) {
        return mIsPresent ? mValue : other;
    }

    public int orElseGet(IntSupplier other) {
        return mIsPresent ? mValue : other.getAsInt();
    }

    public<X extends Throwable> int orElseThrow(Supplier<X> exceptionSupplier) throws X {
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

        if (!(obj instanceof OptionalInt)) {
            return false;
        }

        OptionalInt other = (OptionalInt) obj;
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
                ? String.format("OptionalInt[%s]", mValue)
                : "OptionalInt.empty";
    }
}