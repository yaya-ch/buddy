package com.paymybuddy.buddy.service;

/**
 * @author Yahia CHERIFI
 * This interface provides abstract methods
 * that allow monetizing the PayMyBuddy aplication
 */

public interface MonetizingService {

    /**
     * This method calculates the transaction fee.
     * @param amount amount of money transferred by a user.
     * @return 0.5% of the amount transferred by a user
     * a sum that wil be deposited on the app main account
     */
    Double transactionFee(Double amount);
}
