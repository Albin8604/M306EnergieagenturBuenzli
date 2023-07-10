package ch.gruppe.d.energieagentur.util.files.xml.model.adapter;

import java.time.LocalDate;

/**
 * This class is used for the configuration of the adapters
 */
public class Config {
    public static final String ESL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String SDAT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DECIMAL_FORMAT = "0.0000";
    public static final LocalDate START_FROM_DATE = LocalDate.of(2019, 1, 1);
    public static final LocalDate START_TO_DATE = LocalDate.of(2019, 3, 31);
}
