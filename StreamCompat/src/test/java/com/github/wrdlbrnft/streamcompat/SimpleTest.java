package com.github.wrdlbrnft.streamcompat;

import android.annotation.TargetApi;

import org.junit.Test;

import java.util.function.IntBinaryOperator;
import java.util.stream.IntStream;

/**
 * Created with Android Studio
 * User: Xaver
 * Date: 11/09/16
 */
@TargetApi(24)
public class SimpleTest {

    interface IntTernaryFunction<R> {
        R apply(int a, int b, int c);
    }

    @Test
    public void testMultiplicationTable() {
        final int multiplicationTableResult = printMultiplicationTable(
                123,
                (a, b) -> a * b,
                (number, factor, result) -> number + " * " + factor + " = " + result
        );
        printMultiplicationTable(
                multiplicationTableResult,
                (a, b) -> a / b,
                (number, factor, result) -> number + " / " + factor + " = " + result
        );
    }

    private int printMultiplicationTable(int number, IntBinaryOperator function, IntTernaryFunction<String> resultFormatterFunction) {
        final int[] buffer = new int[]{number};
        IntStream.range(2, 10)
                .mapToObj(i -> {
                    final int valueBefore = buffer[0];
                    buffer[0] = function.applyAsInt(valueBefore, i);
                    return resultFormatterFunction.apply(valueBefore, i, buffer[0]);
                })
                .forEach(System.out::println);
        return buffer[0];
    }
}
