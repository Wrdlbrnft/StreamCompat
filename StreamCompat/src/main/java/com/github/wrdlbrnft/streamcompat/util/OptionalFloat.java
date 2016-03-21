package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.FloatConsumer;
import com.github.wrdlbrnft.streamcompat.function.FloatSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;

import java.util.NoSuchElementException;

public class OptionalFloat {

    private static final OptionalFloat EMPTY = new OptionalFloat();

    private final boolean mIsPresent;
    private final float mValue;

    private OptionalFloat() {
        this.mIsPresent = false;
        this.mValue = Float.NaN;
    }

    public static OptionalFloat empty() {
        return EMPTY;
    }

    private OptionalFloat(float value) {
        this.mIsPresent = true;
        this.mValue = value;
    }

    public static OptionalFloat of(float value) {
        return new OptionalFloat(value);
    }

    public float getAsFloat() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(FloatConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public float orElse(float other) {
        return mIsPresent ? mValue : other;
    }

    public float orElseGet(FloatSupplier other) {
        return mIsPresent ? mValue : other.getAsFloat();
    }

    public<X extends Throwable> float orElseThrow(Supplier<X> exceptionSupplier) throws X {
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

        if (!(obj instanceof OptionalFloat)) {
            return false;
        }

        OptionalFloat other = (OptionalFloat) obj;
        return (mIsPresent && other.mIsPresent)
                ? Float.compare(mValue, other.mValue) == 0
                : mIsPresent == other.mIsPresent;
    }

    @Override
    public int hashCode() {
        return mIsPresent ? Utils.hashCode(mValue) : 0;
    }

    @Override
    public String toString() {
        return mIsPresent
                ? String.format("OptionalFloat[%s]", mValue)
                : "OptionalFloat.empty";
    }
}