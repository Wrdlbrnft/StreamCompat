package com.github.wrdlbrnft.streamcompat.optionals;

import com.github.wrdlbrnft.streamcompat.function.ByteConsumer;
import com.github.wrdlbrnft.streamcompat.function.ByteSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.NoSuchElementException;

public class OptionalByte {

    private static final OptionalByte EMPTY = new OptionalByte();

    private final boolean mIsPresent;
    private final byte mValue;

    private OptionalByte() {
        this.mIsPresent = false;
        this.mValue = 0;
    }

    public static OptionalByte empty() {
        return EMPTY;
    }

    private OptionalByte(byte value) {
        this.mIsPresent = true;
        this.mValue = value;
    }

    public static OptionalByte of(byte value) {
        return new OptionalByte(value);
    }

    public byte getAsByte() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(ByteConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public byte orElse(byte other) {
        return mIsPresent ? mValue : other;
    }

    public byte orElseGet(ByteSupplier other) {
        return mIsPresent ? mValue : other.getAsByte();
    }

    public<X extends Throwable> byte orElseThrow(Supplier<X> exceptionSupplier) throws X {
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

        if (!(obj instanceof OptionalByte)) {
            return false;
        }

        OptionalByte other = (OptionalByte) obj;
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
                ? String.format("OptionalByte[%s]", mValue)
                : "OptionalByte.empty";
    }
}