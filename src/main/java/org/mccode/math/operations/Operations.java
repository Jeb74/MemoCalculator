package org.mccode.math.operations;

import java.util.Arrays;

public enum Operations {
    ADD("+", (byte)3),
    SUB("-", (byte)4),
    MUL("*", (byte)5),
    DIV("/", (byte)6),
    MOD("%", (byte)6),
    POW("^", (byte)7),
    LOG("ln", (byte)8),
    SQRT("sqrt", (byte)8);

    private final String op;
    private final byte precedence;

    Operations(String op, byte precedence) {
        this.op = op;
        this.precedence = precedence;
    }

    public static Integer getPrecedence(String operator) {
        return Arrays.stream(Operations.values())
                .filter(e -> e.op.equals(operator))
                .findFirst()
                .map(e -> {return (int) e.precedence;})
                .orElseGet(() -> 9);
    }

    public static boolean isOperator(String operator) {
        return Arrays.stream(Operations.values()).anyMatch(o -> o.op.equals(operator));
    }
}
