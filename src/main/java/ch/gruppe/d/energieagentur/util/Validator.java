package ch.gruppe.d.energieagentur.util;

import java.util.Collection;

/**
 * This class is a validator
 */
public class Validator {
    public static boolean isNullOrEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }
}
