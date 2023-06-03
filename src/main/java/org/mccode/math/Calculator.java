package org.mccode.math;

import java.util.Stack;
import javafx.util.Pair;

public abstract class Calculator {

    private enum Operations {
        ADD("+", 2),
        SUB("-", 3),
        DIV("/", 5),
        MUL("*", 4),
        POW("^", 6),
        LOG("log", 1),
        SQRT("sqrt", 1);

        private final String op;
        private final int precedence;
        Operations(String op, int precedence) {
            this.op = op;
            this.precedence = precedence;
        }

        public int getPrecedence() {
            return precedence;
        }

        public String getOperator() {
            return op;
        }


    }

    public final Double execute(String expression, Double[] variables) {
        Stack<Operations> op = new Stack<>();
        Stack<Double> val = new Stack<>();
        int curr_precedence = 0;
        StringBuilder curr_operation = new StringBuilder();
        int start = -1;
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case (' ') -> {
                    continue;
                }
                case ('(') -> {
                    curr_precedence += 10;
                }
                case (')') -> {
                    curr_precedence -= 10;
                }
                default -> {
                    if (Character.isDigit(c) && start == -1) start = i;
                    else if (!Character.isDigit(c) && c != '.' && start != -1) val.add(buildNumber(expression, start, i));
                }
            }
        }
    }
/*
10 + 10 -> 10, 10
           +
           20

(10 + 10) * 10 - 10 ->
                     10, 10, 10
                     + *
                       -
                     10, 100, 10
                     + -
                     10, 90
                     +
                     100
 */
    private Double buildNumber(String expression, int start, int end) {
        return Double.parseDouble(expression.substring(start, end));
    }

}
