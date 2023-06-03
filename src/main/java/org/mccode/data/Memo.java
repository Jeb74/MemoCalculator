package org.mccode.data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Memo {
    private static final HashMap<Expression, HashMap<Double[], Double>> HISTORY = new HashMap<>();
    private static final LinkedList<Variable> VARIABLES = new LinkedList<>();

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

    public static void addValToHistory(String var, double value) {
        var tmp = new Variable(var, value);
        var tempValue = VARIABLES.stream().filter(variable -> variable.isLast() && variable.getGroup().equals(var)).findFirst();
        tempValue.ifPresent(Variable::changeRole);
        if (!VARIABLES.contains(tmp)) VARIABLES.add(tmp);
    }

    public static Double[] getValHistory(String filterBy) {
        if (filterBy != null) return VARIABLES.stream().filter(v -> Objects.equals(v.getGroup(), filterBy)).map(Variable::getValue).toArray(Double[]::new);
        return VARIABLES.stream().map(Variable::getValue).toArray(Double[]::new);
    }

    //TODO: salvare solo le chiavi della prima HASHMAP di HISTORY come NOME : ESPRESSIONE
    public static void saveHistory() {
        return;
    }
}
