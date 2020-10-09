package com.paymybuddy.buddy.enums;

/**
 * @author Yahia CHERIFI
 * This enum provides constants that give additional information
 * about transactions
 */

public enum TransactionProperty {

    /**
     * Defined when money in sent.
     */
    SENT,

    /**
     * Defined when money is received.
     */
    RECEIVED,

    /**
     * Defined when sending money fail.
     */
    SENDING_FAILED,

    /**
     * Defined when depositing money ob buddy account fails.
     */
    DEPOSITING_FAILED
}
