package ch.gruppe.d.energieagentur;

import java.time.LocalDate;

/**
 * This class is used for configurations of multiple classes
 */
public class Config {
    public static final String ESL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SDAT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DECIMAL_FORMAT = "0.0000";
    public static final LocalDate START_FROM_DATE = LocalDate.of(2021, 1, 1);
    public static final LocalDate START_TO_DATE = LocalDate.of(2021, 2, 1);
}
