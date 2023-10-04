package org.mccode.utils;

import org.mccode.utils.RuleSetterUtils.Action;

import java.util.HashMap;

public final class RuleSetter<R,E> {

    Action<R,E> defaultAction = null;

    private final HashMap<E, Action<R,E>> register = new HashMap<>();

    public RuleSetter(Action<R,E> defaultAction) {
        this.defaultAction = defaultAction;
    }

    public RuleSetter() {}

    public void link(E value, Action<R,E> action) {
        register.putIfAbsent(value, action);
    }

    public void update(E value, Action<R,E> action) {
        register.put(value, action);
    }

    public R get(E value) {
        return register.get(value).perform(value);
    }

    public R getOrDefault(E value) {
        return register.containsKey(value) ? register.get(value).perform(value) : defaultAction.perform(value);
    }

    public R getOrElse(E value, Action<R,E> action) {
        return register.containsKey(value) ? register.get(value).perform(value) : action.perform(value);
    }

}
