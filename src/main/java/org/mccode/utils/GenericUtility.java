package org.mccode.utils;

import java.util.function.Predicate;

public interface GenericUtility {
    @SafeVarargs
    static <T> boolean any(Predicate<T> rule, T ... elements) {
        for (T e : elements) {
            if (rule.test(e)) return true;
        }
        return false;
    }
}
