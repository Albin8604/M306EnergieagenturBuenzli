package ch.gruppe.d.energieagentur.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static ch.gruppe.d.energieagentur.util.Date.Formatter.*;

/**
 * This class is used to make the conversion from one thing to another easier
 */
public class Converter {

    /**
     * Converts LocalDateTime to milliseconds
     * @param localDateTime given time
     * @return milliseconds
     */
    public static long convertLocalDateTimeToMillis(LocalDateTime localDateTime){
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
