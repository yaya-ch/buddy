package com.paymybuddy.buddy.enums;

/**
 * @author Yahia CHERIFI
 * This enum groups the possible transaction status
 */

public enum TransactionStatusInfo {

    /**
     * This is the first status of a transaction before treatment.
     * It appears on the sender's related transaction
     */
    TRANSACTION_IN_PROGRESS,

    /**
     * Transaction accepted: money transferred successfully.
     * It appears on the sender's related transaction
     */
    TRANSACTION_ACCEPTED,

    /**
     * Transaction rejected: money not transferred.
     * It appears on the sender's related transaction
     */
    TRANSACTION_REJECTED,
}
