package com.paymybuddy.buddy.exceptions;

/**
 * @author Yahia CHERIFI
 * This exception is thrown when problems occur during money transfering
 * was found in database
 */

public class MoneyOpsException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message.
     */
    public MoneyOpsException(final String message) {
        super(message);
    }
}
