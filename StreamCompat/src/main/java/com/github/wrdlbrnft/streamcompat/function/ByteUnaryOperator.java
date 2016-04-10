package com.github.wrdlbrnft.streamcompat.function;

public interface ByteUnaryOperator {

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    byte applyAsByte(byte operand);

}