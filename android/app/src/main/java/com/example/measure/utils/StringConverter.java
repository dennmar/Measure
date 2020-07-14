package com.example.measure.utils;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Converts objects into strings.
 */
public class StringConverter {
    private static final String DATE_STR_FORMAT = "MMMM d, YYYY";

    /**
     * Return the string representation of the given local date.
     *
     * @param date local date to convert
     * @return a string representing the local date (ex: "January 2, 2020")
     */
    public static String localDateToString(LocalDate date) {
        return date.toString(DATE_STR_FORMAT);
    }

    /**
     * Return a local date that represents the given string.
     *
     * @param dateStr string of date to convert in the format
     *                "[Month] [day], [year]" (ex: "March 2, 1932")
     * @return the local date that represents the given string
     */
    public static LocalDate toLocalDate(String dateStr) {
        if (dateStr == null || dateStr.equals("")) {
            return null;
        }

        DateTimeFormatter dateFormatter =
                DateTimeFormat.forPattern(DATE_STR_FORMAT);
        return dateFormatter.parseLocalDate(dateStr);
    }
}
