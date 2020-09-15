package com.paymybuddy.buddy.enums;

/**
 * @author Yahia CHERIFI
 * This enum groups the different transaction natures
 */

public enum TransactionNature {

    /**
     * This type of transaction refers
     * to transaction between Buddy account and personal bank account.
     */
    BETWEEN_ACCOUNTS("To bank account"),

    /**
     * This type of transaction designs
     * transactions between a user and his/her contacts.
     */
    TO_CONTACTS("To contact");

    /**
     * Transaction destination: to contacts or to linked bank account.
     */
    private final String destination;

    /**
     * Enum constructor.
     * @param recipient money recipient:
     * either a user's contact or user's bank account.
     */
    TransactionNature(final String recipient) {
        this.destination = recipient;
    }
}
