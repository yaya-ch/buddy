package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;

/**
 * @author Yahia CHERIFI
 * This interface provides abstract methods
 * that deal with money transfer operations
 */

public interface MoneyOpsService {

    /**
     * This method provides the logic that will.
     * be used when depositing money on the buddy account
     * @param email the user's email
     * @param iban the associated bank account's iban
     * @param amount amount of money to deposit
     * @param description a message in which the sender describes transactions
     * @throws ElementNotFoundException if no user with the matching email found
     * @throws MoneyOpsException if errors occur while transferring money
     */
    void depositMoneyOnBuddyAccount(String email,
                                    String iban,
                                    Double amount,
                                    String description)
            throws ElementNotFoundException, MoneyOpsException;

    /**
     * This method provides the logic that will.
     * be used in transferring money to users' bank accounts
     * @param email the user's email
     * @param iban the user's bank account iban
     * @param amount the amount that users want
     *               to transfer to their bank accounts
     * @param description a message in which the sender describes transactions
     * @throws ElementNotFoundException if no user/user found in db
     * @throws MoneyOpsException if errors occur while transferring money
     */
    void transferMoneyToBankAccount(String email, String iban,
                                    Double amount, String description)
            throws ElementNotFoundException, MoneyOpsException;

    /**
     * This method provides the logic that will.
     * be used in transferring money to other users
     * @param senderEmail the email of the sender
     * @param receiverEmail the email of the user who will receive money
     * @param amount the amount that will be sent
     * @param description a message in which the sender describes transactions
     * @throws MoneyOpsException if errors occur while transferring money
     * @throws ElementNotFoundException if no user/users found in db
     */
    void sendMoneyToUsers(String senderEmail,
                          String receiverEmail,
                          Double amount,
                          String description)
            throws MoneyOpsException, ElementNotFoundException;
}
