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
    SENDING_IN_PROGRESS,

    /**
     * This is the first status of a transaction before treatment.
     * It appears on the recipient's related transaction
     */
    UP_COMING_TRANSACTION,

    /**
     * This is the final status of a transaction(transaction treated).
     * It appears on the recipient's related transaction
     */
    MONEY_RECEIVED,

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
