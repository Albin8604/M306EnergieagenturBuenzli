package ch.gruppe.d.energieagentur.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static ch.gruppe.d.energieagentur.util.Date.Formatter.*;

/**
 * This class is a converter
 */
public class Converter {

    /**
     * This method converts a date to a local date time
     *
     * @param dateToConvert Date
     * @return LocalDateTime
     */
    public static LocalDateTime dateToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * This method converts a date to a local date
     *
     * @param dateToConvert Date
     * @return LocalDate
     */
    public static LocalDate dateToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * This method converts a date to a local time
     *
     * @param localDateTimeToConvert LocalDateTime
     * @return LocalTime
     */
    public static Date LocalDateTimetoDate(LocalDateTime localDateTimeToConvert) {
        return Date.from(localDateTimeToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * This method converts a local date time to a date
     *
     * @param text String
     * @return Date
     */
    public static LocalDate convertTextToLocalDate(String text) {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * This method converts a local time to a date
     *
     * @param text String
     * @return Date
     */
    public static LocalTime convertTextToLocalTime(String text) {
        return LocalTime.parse(text, DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method converts a local date time to a date
     *
     * @param text String
     * @return Date
     */
    public static LocalDateTime convertTextToLocalDateTime(String text) {
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    /**
     * This method converts a date to a text
     *
     * @param localDate LocalDate
     * @return String
     */
    public static String convertLocalDateToText(LocalDate localDate) {
        return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    /**
     * This method converts a date to a text
     *
     * @param localTime LocalTime
     * @return String
     */
    public static String convertLocalTimeToText(LocalTime localTime) {
        return localTime.format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    /**
     * This method converts a date to a text
     *
     * @param localDateTime LocalDateTime
     * @return String
     */
    public static String convertLocalDateToText(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    /**
     * This method converts a date to a text
     *
     * @param b byte
     * @return String
     */
    public static boolean convertByteToBoolean(byte b) {
        return b == 1;
    }

    /**
     * This method converts a date to a text
     *
     * @param b byte
     * @return String
     */
    public static byte convertBooleanToByte(boolean b) {
        return (byte) (b ? 1 : 0);
    }
}
