package com.paymybuddy.buddy.exceptions;

/**
 * @author Yahia CHERIFI
 * This exception is thrown when no matching element
 * was found in database
 */
public class ElementNotFoundException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public ElementNotFoundException(final String message) {
        super(message);
    }
}
