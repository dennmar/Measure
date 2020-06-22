package com.example.measure.utils;

/**
 * An exception that occurs when carrying out an operation on a database.
 */
public class DBOperationException extends Exception {
    /**
     * Construct an exception with the given message.
     *
     * @param msg error message
     */
    public DBOperationException(String msg) {
        super(msg);
    }
}
