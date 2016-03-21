package com.github.wrdlbrnft.streamcompat.util;

import com.github.wrdlbrnft.streamcompat.function.Consumer;
import com.github.wrdlbrnft.streamcompat.function.Function;
import com.github.wrdlbrnft.streamcompat.function.Predicate;
import com.github.wrdlbrnft.streamcompat.function.Supplier;

import java.util.NoSuchElementException;

public class Optional<T> {

    private static final Optional<?> EMPTY = new Optional<>();

    private final T mValue;

    private Optional() {
        this.mValue = null;
    }

    public static <T> Optional<T> empty() {
        @SuppressWarnings("unchecked")
        Optional<T> t = (Optional<T>) EMPTY;
        return t;
    }

    private Optional(T value) {
        this.mValue = Utils.requireNonNull(value);
    }

    public static <T> Optional<T> of(T value) {
        return new Optional<>(value);
    }

    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    public T get() {
        if (mValue == null) {
            throw new NoSuchElementException("No value present");
        }
        return mValue;
    }

    public boolean isPresent() {
        return mValue != null;
    }

    public void ifPresent(Consumer<? super T> consumer) {
        if (mValue != null)
            consumer.accept(mValue);
    }

    public Optional<T> filter(Predicate<? super T> predicate) {
        Utils.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(mValue) ? this : empty();
        }
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> mapper) {
        Utils.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Optional.ofNullable(mapper.apply(mValue));
        }
    }

    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) {
        Utils.requireNonNull(mapper);
        if (!isPresent())
            return empty();
        else {
            return Utils.requireNonNull(mapper.apply(mValue));
        }
    }

    public T orElse(T other) {
        return mValue != null ? mValue : other;
    }

    public T orElseGet(Supplier<? extends T> other) {
        return mValue != null ? mValue : other.get();
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (mValue != null) {
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

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return Utils.equals(mValue, other.mValue);
    }

    @Override
    public int hashCode() {
        return Utils.hashCode(mValue);
    }

    @Override
    public String toString() {
        return mValue != null
                ? String.format("Optional[%s]", mValue)
                : "Optional.empty";
    }
}