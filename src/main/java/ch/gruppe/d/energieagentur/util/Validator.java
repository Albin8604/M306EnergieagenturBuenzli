package ch.gruppe.d.energieagentur.util;

import java.util.Collection;

public class Validator {
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
