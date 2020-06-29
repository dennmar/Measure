package com.example.measure.utils;

/**
 * An amount of time.
 */
public class Duration {
    private long seconds;

    /**
     * Initialize member variables.
     *
     * @param seconds amount of time in seconds
     */
    public Duration(long seconds) {
        this.seconds = seconds;
    }

    /**
     * Retrieve the length of the duration in seconds.
     *
     * @return amount of seconds for this duration
     */
    public long getSeconds() {
        return seconds;
    }
}
