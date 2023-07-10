package ch.gruppe.d.energieagentur.util.Date;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * This class is used to manage all the Date data
 */
public class DateHelper {
    public static boolean isDateBetweenDatesBasedOnMonth(LocalDate date, LocalDate from, LocalDate to) {
        return from.getMonthValue() + from.getYear() <= date.getMonthValue() + from.getYear() && to.getMonthValue() + from.getYear() >= date.getMonthValue() + from.getYear();
    }

    /**
     * Checks if a date is between two dates
     *
     * @param date date to check
     * @param from start date
     * @param to   end date
     * @return true if date is between from and to
     */
    public static boolean isTimeBetweenTimes(LocalTime date, LocalTime from, LocalTime to) {
        return (from.isBefore(date) || from.equals(date)) && (to.isAfter(date) || to.equals(date));
    }
}
