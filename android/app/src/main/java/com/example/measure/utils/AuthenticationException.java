package com.example.measure.utils;

/**
 * An exception that occurs when trying the authenticate.
 */
public class AuthenticationException extends Exception {
    /**
     * Construct an exception with the given message.
     *
     * @param msg error message
     */
    public AuthenticationException(String msg) {
        super(msg);
    }
}
