package org.mccode.data;

import java.util.ArrayList;
import java.util.HashMap;

public class Memo {
    private static final HashMap<Expression, HashMap<Double[], Double>> HISTORY = new HashMap<>();
    //private static final ArrayList<> variables = new HashMap<>(); TODO
    public static void save(Expression expression, Double[] variables, Double result) {
        if (!HISTORY.containsKey(expression)) HISTORY.put(expression, new HashMap<>());
        var tmp = HISTORY.get(expression);
        if (!tmp.containsKey(variables)) tmp.put(variables, result);
    }

    public static Double query(Expression expression, Double[] variables) {
        if (!HISTORY.containsKey(expression) || !HISTORY.get(expression).containsKey(variables)) {
            return null;
        }
        return HISTORY.get(expression).get(variables);
    }
    // PROFIT
    // <PROFIT>
    public static void saveHistory() {
        return;
    }
}
