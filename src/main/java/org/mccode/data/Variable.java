package org.mccode.data;

public final class Variable {
    private final String group;
    private final double value;
    private boolean isLast = true;

    public Variable(String var, double value) {
        group = var;
        this.value = value;
    }

    public boolean isLast() {
        return isLast;
    }
    public void changeRole() {
        isLast = !isLast;
    }
    public double getValue() {
        return value;
    }

    public String getGroup() {
        return group;
    }
 }
