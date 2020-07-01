package com.example.measure.utils;

/**
 * An exception that occurs with an invalid database query.
 */
public class InvalidQueryException extends Exception {
    /**
     * Construct an exception with the given message.
     *
     * @param msg error message
     */
    public InvalidQueryException(String msg) {
        super(msg);
    }
}
