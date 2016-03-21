package com.github.wrdlbrnft.streamcompat.function;

public interface LongBinaryOperator {

    /**
     * Applies this operator to the given operands.
     *
     * @param left the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsInt(long left, long right);
}