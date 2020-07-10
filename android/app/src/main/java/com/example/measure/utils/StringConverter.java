package com.example.measure.utils;

import org.joda.time.LocalDate;

/**
 * Converts objects into strings.
 */
public class StringConverter {
    /**
     * Return the string representation of the given local date.
     *
     * @param date local date to convert
     * @return a string representing the local date (ex: "January 2, 2020")
     */
    public static String localDateToString(LocalDate date) {
        return date.toString("MMMM d, YYYY");
    }
}
