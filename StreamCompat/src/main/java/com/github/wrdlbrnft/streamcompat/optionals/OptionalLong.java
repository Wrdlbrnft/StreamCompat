package com.github.wrdlbrnft.streamcompat.optionals;

import com.github.wrdlbrnft.streamcompat.function.LongConsumer;
import com.github.wrdlbrnft.streamcompat.function.LongSupplier;
import com.github.wrdlbrnft.streamcompat.function.Supplier;
import com.github.wrdlbrnft.streamcompat.util.Utils;

import java.util.NoSuchElementException;

public final class OptionalLong {

    private static final OptionalLong EMPTY = new OptionalLong();

    private final boolean mIsPresent;
    private final long mValue;

    private OptionalLong() {
        this.mIsPresent = false;
        this.mValue = 0;
    }

    public static OptionalLong empty() {
        return EMPTY;
    }

    private OptionalLong(long value) {
        this.mIsPresent = true;
        this.mValue = value;
    }

    public static OptionalLong of(long value) {
        return new OptionalLong(value);
    }

    public long getAsLong() {
        if (!mIsPresent) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    public boolean isPresent() {
        return mIsPresent;
    }

    public void ifPresent(LongConsumer consumer) {
        if (mIsPresent) {
            consumer.accept(mValue);
        }
    }

    public long orElse(long other) {
        return mIsPresent ? mValue : other;
    }

    public long orElseGet(LongSupplier other) {
        return mIsPresent ? mValue : other.getAsLong();
    }

    public <X extends Throwable> long orElseThrow(Supplier<X> exceptionSupplier) throws X {
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

        if (!(obj instanceof OptionalLong)) {
            return false;
        }

        OptionalLong other = (OptionalLong) obj;
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
                ? String.format("OptionalLong[%s]", mValue)
                : "OptionalLong.empty";
    }
}