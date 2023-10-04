package org.mccode.utils.RuleSetterUtils;

@FunctionalInterface
public interface Action<R,P> {
    R perform(P parameters);
}
