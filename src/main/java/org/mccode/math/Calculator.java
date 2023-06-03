package org.mccode.math;


import java.util.ArrayList;

public abstract class Calculator {

    /*
    10 - 20 + (20 - 10)
     */
    private enum Operations {
        ADD("+", 3),
        SUB("-", 4),
        MUL("*", 5),
        DIV("/", 6),
        POW("^", 7),
        LOG("log", 8),
        SQRT("sqrt", 8);

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
        //TODO rimuovere i metodi attuali e introdurre un metodo per ottenere la precedenza sulla base dell'operatore passato

    }
    /*
    10 + 10 - 9 * 5

     */

    public final Double execute(String expression, Double[] variables) {
        TreeNode<String> tree = null;
        TreeNode<String> curr_node = null;
        StringBuilder builder = new StringBuilder();
        for (int i = 0, start = -1; i < expression.length(); i++) {
            if (Character.isDigit(expression.charAt(i))) {
                start = i;
                for (int j = i+1; j < expression.length(); j++) {
                    if (!Character.isDigit(expression.charAt(j))) {
                        break;
                    }
                }
            }
            if (tree == null && curr_node != null) tree = curr_node;
        }
        return evaluateTree(tree);
        //TODO finire il parser delle espressioni: manca il parsing degli operatori e delle funzioni + il parsing dell'operatore negazione
    }
    /*
    FLAGS:
    VALUE/VARIABLE  : 0
    OPERATOR        : 1
    FUNCTION        : 2
    PRECEDENCE:
    VALUE           : 9
    OPERATOR        : 5
    FUNCTION        : 8
    BRACKETS        : +10
     */
    private TreeNode<String> buildTree(TreeNode<String> currentNode, String value, byte flag, int precedence) {
        if (currentNode == null) return new TreeNode<>(value, flag);
        //TODO finire la struttura del costruttore dell'albero
        return currentNode;
    }

    private double evaluateTree(TreeNode<String> tree) {
        return 0.0;
    }
}
