package com.github.wrdlbrnft.streamcompat;

public class StringJoiner {
    private final String prefix;
    private final String delimiter;
    private final String suffix;
    private StringBuilder value;
    private String emptyValue;

    public StringJoiner(CharSequence delimiter, CharSequence prefix, CharSequence suffix) {
        Utils.requireNonNull(prefix, "The prefix must not be null");
        Utils.requireNonNull(delimiter, "The delimiter must not be null");
        Utils.requireNonNull(suffix, "The suffix must not be null");

        this.prefix = prefix.toString();
        this.delimiter = delimiter.toString();
        this.suffix = suffix.toString();
        this.emptyValue = this.prefix + this.suffix;
    }

    public StringJoiner setEmptyValue(CharSequence emptyValue) {
        this.emptyValue = Utils.requireNonNull(emptyValue, "The empty value must not be null").toString();
        return this;
    }

    @Override
    public String toString() {
        if (value == null) {
            return emptyValue;
        } else {
            if (suffix.equals("")) {
                return value.toString();
            } else {
                int initialLength = value.length();
                String result = value.append(suffix).toString();
                value.setLength(initialLength);
                return result;
            }
        }
    }

    public <T> StringJoiner add(T newElement) {
        prepareBuilder().append(newElement);
        return this;
    }

    public StringJoiner merge(StringJoiner other) {
        Utils.requireNonNull(other);
        if (other.value != null) {
            final int length = other.value.length();
            prepareBuilder().append(other.value, other.prefix.length(), length);
        }
        return this;
    }

    private StringBuilder prepareBuilder() {
        if (value != null) {
            value.append(delimiter);
        } else {
            value = new StringBuilder().append(prefix);
        }
        return value;
    }

    public int length() {
        return value != null
                ? value.length() + suffix.length()
                : emptyValue.length();
    }
}