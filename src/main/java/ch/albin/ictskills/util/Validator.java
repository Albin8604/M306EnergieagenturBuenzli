package ch.albin.ictskills.util;

import java.util.Collection;

public class Validator {
    public static boolean isNullOrEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
