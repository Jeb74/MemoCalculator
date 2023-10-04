package org.mccode.math;


import org.apache.commons.math3.util.Pair;
import org.mccode.data.Memo;
import org.mccode.math.operations.Operations;
import org.mccode.utils.GenericUtility;
import org.mccode.utils.RuleSetter;
import org.mccode.utils.TreeNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class Calculator {

    private final String EXP_REFERENCE = "<(\\w*(?:\\:\\s*\\w+(?:,\\s*\\w+)*|))>";
    private final Pattern VAR_REFERENCE = Pattern.compile("\\b((?![0-9])(?!sqrt\\b|ln\\b)\\w+)\\b(?!:)");


    private enum Move {
        EXCHANGE_LEFT,
        EXCHANGE_RIGHT,
        LEFT,
        RIGHT
    }


    public final Double execute(String expression, Double[] vars) {
        HashMap<String, Double> variables = new HashMap<>();
        var placeholders = getPlaceholders(expression);
        for (int i = 0; vars != null && i < vars.length && i < placeholders.length; i++) {
            variables.put(placeholders[i], vars[i]);
        }
        return evaluateTree(buildTree(expression), variables);
    }

    /*
    10 - 20 + (20 - 10)
     */
    // (10.2-(40+50))*60+12
    // 10.2 p 10
    // - p 14
    private TreeNode<String> addTreeNode(TreeNode<String> tree, String value, int precedence) {
        var node = new TreeNode<>(value, precedence + Operations.getPrecedence(value));
        if (tree == null) return node;
        if (tree.getPrecedence() > node.getPrecedence()) {
            node.setLeft(tree);
            return node;
        }
        var _package = getLeafPos(tree, node.getPrecedence());
        var leaf = _package.getFirst();
        var mv = _package.getSecond();
        if (mv == Move.LEFT) leaf.setLeft(node);
        else if (mv == Move.RIGHT) leaf.setRight(node);
        else if (mv == Move.EXCHANGE_LEFT) {
            node.setLeft(leaf.getLeft());
            leaf.setLeft(node);
        }
        else if (mv == Move.EXCHANGE_RIGHT) {
            node.setLeft(leaf.getRight());
            leaf.setRight(node);
        }
        return tree;
    }

    private Pair<TreeNode<String>, Move> getLeafPos(TreeNode<String> tree, int nPrec) {
        var isSingleFlow = tree.getPrecedence() % 10 == 8;
        if (tree.getLeft() == null) return new Pair<>(tree, Move.LEFT);
        if (isSingleFlow) {
            if (tree.getLeft().getPrecedence() >= nPrec) return new Pair<>(tree, Move.EXCHANGE_LEFT);
            else return getLeafPos(tree.getLeft(), nPrec);
        }
        if (tree.getRight() == null) return new Pair<>(tree, Move.RIGHT);
        else if (tree.getRight().getPrecedence() >= nPrec) return new Pair<>(tree, Move.EXCHANGE_RIGHT);
        else return getLeafPos(tree.getRight(), nPrec);
    }

    private String[] getPlaceholders(String expression) {
        Matcher matcher = VAR_REFERENCE.matcher(expression);
        return matcher.results().map(mr -> mr.group(1)).distinct().toArray(String[]::new);
    }

    private TreeNode<String> buildTree(String exp) {
        RuleSetter<Integer, Character> rules = new RuleSetter<>(c -> {return 0;});
        rules.link('(', c -> {return 10;});
        rules.link(')', c -> {return -10;});
        TreeNode<String> tree = null;
        int precedence = 0;
        StringBuilder value = new StringBuilder();
        String lastElementAdded = "";
        for (char i : exp.toCharArray()) {
            if (i == ' ') continue;
            if (GenericUtility.any(c -> i == c, '(', ')', '+', '*', '-', '/', '^', '%')) {
                if (!value.isEmpty()) {
                    lastElementAdded = value.toString();
                    tree = addTreeNode(tree, lastElementAdded, precedence);
                    value = new StringBuilder();
                }
                if (GenericUtility.any(c -> i == c, '+', '*', '-', '/', '^', '%')) {
                    if ((isOperator(lastElementAdded) || lastElementAdded.equals("")) && i == '-') {
                        value.append(i);
                        continue;
                    }
                    lastElementAdded = String.valueOf(i);
                    tree = addTreeNode(tree, lastElementAdded, precedence);
                }
                precedence += rules.getOrDefault(i);
                continue;
            }
            value.append(i);
        }
        if (!value.isEmpty()) tree = addTreeNode(tree, value.toString(), precedence);
        return tree;
    }

    public boolean isOperator(String value) {
        return Operations.isOperator(value);
    }

    private double evaluateTree(TreeNode<String> tree, HashMap<String, Double> variables) {
        if (!isOperator(tree.getValue())) return evaluate(tree.getValue(), variables);
        var nodeL = tree.getLeft();
        var nodeR = tree.getRight();
        if (nodeL != null) {
            if (nodeR != null) {
                return operate(evaluateTree(nodeL, variables), tree, evaluateTree(nodeR, variables));
            }
            else {
                return operate(evaluateTree(nodeL, variables), tree);
            }
        }
        else {
            throw new RuntimeException("Expression not formatted correctly");
        }
    }

    private double evaluate(String value, HashMap<String, Double> variables) {
        if (value.matches(EXP_REFERENCE)) {
            value = value.substring(1, value.length()-1);
            var tokens = value.split(":");
            var name = tokens[0].strip();
            tokens = Arrays.stream(tokens[1].split(",")).map(String::strip).toArray(String[]::new);
            String expression = Memo.getExpression(name);
            return execute(expression, valuesForFunc(tokens, variables));
        }
        if (variables.containsKey(value)) return variables.get(value);
        return Double.parseDouble(value);
    }

    private Double[] valuesForFunc(String[] values, HashMap<String, Double> variables) {
        List<Double> list = new LinkedList<>();
        for (String i : values) {
            var val = 0.0;
            try {
                val = Double.parseDouble(i);
            } catch (NumberFormatException ignored) {
                val = variables.getOrDefault(i, 0.0);
            }
            list.add(val);
        }
        return list.toArray(Double[]::new);
    }

    private double operate(double valueL, TreeNode<String> op, double valueR) {
        switch (op.getValue()) {
            case "+": return valueL + valueR;
            case "-": return valueL - valueR;
            case "*": return valueL * valueR;
            case "/": return valueL / valueR;
            case "%": return valueL % valueR;
            case "^": return Math.pow(valueL, valueR);
            default: return 0.0;
        }
    }

    private double operate(double valueL, TreeNode<String> op) {
        switch (op.getValue()) {
            case "ln": return Math.log(valueL);
            case "sqrt": return Math.sqrt(valueL);
            default: return 0.0;
        }
    }
}
