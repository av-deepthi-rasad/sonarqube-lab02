package com.example;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.IntBinaryOperator;

public class Calculator {

    /**
     * Supported calculator operations
     */
    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        MODULO,
        POWER
    }

    private static final Map<Operation, IntBinaryOperator> OPERATIONS =
            new EnumMap<>(Operation.class);

    static {
        OPERATIONS.put(Operation.ADD, Integer::sum);
        OPERATIONS.put(Operation.SUBTRACT, (a, b) -> a - b);
        OPERATIONS.put(Operation.MULTIPLY, (a, b) -> a * b);
        OPERATIONS.put(Operation.MODULO, (a, b) -> a % b);
        OPERATIONS.put(Operation.DIVIDE, Calculator::safeDivide);
        OPERATIONS.put(Operation.POWER, Calculator::power);
    }

    /**
     * Calculates result for given operands and operation
     */
    public int calculate(int a, int b, Operation operation) {
        Objects.requireNonNull(operation, "Operation must not be null");

        IntBinaryOperator operator = OPERATIONS.get(operation);
        if (operator == null) {
            throw new IllegalArgumentException("Unsupported operation: " + operation);
        }

        return operator.applyAsInt(a, b);
    }

    /**
     * Safe division with validation
     */
    private static int safeDivide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a / b;
    }

    /**
     * Calculates power using iterative multiplication
     */
    private static int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponent not supported");
        }

        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        return result;
    }
}
