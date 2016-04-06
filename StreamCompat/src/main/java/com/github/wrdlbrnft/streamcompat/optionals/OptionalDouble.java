package com.github.wrdlbrnft.streamcompat.optionals;

import com.github.wrdlbrnft.streamcompat.function.DoubleConsumer;
import com.github.wrdlbrnft.streamcompat.function.DoubleSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.NoSuchElementException;

public class OptionalDouble {

    private static final OptionalDouble EMPTY = new OptionalDouble();

    private final boolean mIsPresent;
    private final double mValue;

    private OptionalDouble() {
        this.mIsPresent = false;
        this.mValue = Double.NaN;
    }

    public static OptionalDouble empty() {
        return EMPTY;
    }

    private OptionalDouble(double value) {
        this.mIsPresent = true;
        this.mValue = value;
    }

    public static OptionalDouble of(double value) {
        return new OptionalDouble(value);
    }

    public double getAsDouble() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(DoubleConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public double orElse(double other) {
        return mIsPresent ? mValue : other;
    }

    public double orElseGet(DoubleSupplier other) {
        return mIsPresent ? mValue : other.getAsDouble();
    }

    public<X extends Throwable> double orElseThrow(Supplier<X> exceptionSupplier) throws X {
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

        if (!(obj instanceof OptionalDouble)) {
            return false;
        }

        OptionalDouble other = (OptionalDouble) obj;
        return (mIsPresent && other.mIsPresent)
                ? Double.compare(mValue, other.mValue) == 0
                : mIsPresent == other.mIsPresent;
    }

    @Override
    public int hashCode() {
        return mIsPresent ? Utils.hashCode(mValue) : 0;
    }

    @Override
    public String toString() {
        return mIsPresent
                ? String.format("OptionalDouble[%s]", mValue)
                : "OptionalDouble.empty";
    }
}