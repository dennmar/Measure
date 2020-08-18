package com.example.measure.db;

import androidx.room.TypeConverter;

import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDate;

/**
 * Type converters for the Room entities.
 */
public class RoomConverters {
    /**
     * Convert a local date to UTC seconds since epoch.
     *
     * @param date date to convert
     * @return the date converted to UTC seconds since epoch
     */
    @TypeConverter
    public static long dateToUtcMillis(LocalDate date) {
        return date == null ? -1 :
                date.toDateTimeAtStartOfDay(DateTimeZone.UTC).getMillis();
    }

    /**
     * Convert UTC milliseconds since epoch to a local date.
     *
     * @param utcMillis milliseconds to convert
     * @return the UTC milliseconds converted to a local date
     */
    @TypeConverter
    public static LocalDate fromUtcMillis(long utcMillis) {
        return new LocalDate(utcMillis, DateTimeZone.UTC);
    }

    /**
     * Convert a duration of time to seconds.
     *
     * @param duration time to convert
     * @return the duration of time converted to seconds
     */
    @TypeConverter
    public static long durationToSeconds(Duration duration) {
        return duration == null ? -1 : duration.getStandardSeconds();
    }

    /**
     * Convert seconds to a duration.
     *
     * @param seconds seconds to convert
     * @return the seconds converted to a duration of time
     */
    @TypeConverter
    public static Duration fromSeconds(long seconds) {
        return new Duration(1000 * seconds);
    }
}
