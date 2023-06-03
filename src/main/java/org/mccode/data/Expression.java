package org.mccode.data;

import org.jetbrains.annotations.Nullable;


public class Expression {
    private final String name;
    private final String exp;

    public Expression(String name, String exp) {
        this.name = name;
        this.exp = exp;
    }

    public Expression(String exp) {
        this.exp = exp;
        this.name = "Unknown";
    }

    public String getExpression() {
        return exp;
    }

    public String getName() {
        return exp;
    }

    public String[] get() {
        return new String[]{name, exp};
    }

    @Override
    public int hashCode() {
        return exp.hashCode();
    }
}
