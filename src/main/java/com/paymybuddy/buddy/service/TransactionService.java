package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * This interface contains abstract methods that provide
 * the logic to operated on the data sent to and from
 * the controllers and the repository layer
 */

public interface TransactionService {

    /**
     * Find transactions by id.
     * @param id the transaction id
     * @return the found transaction
     * @throws ElementNotFoundException if no transaction was found
     */
    TransactionDTO findById(Integer id) throws ElementNotFoundException;

    /**
     * Retrieve a list of a given user's transactions.
     * @param id the user's id
     * @return a list of transactions
     * @throws ElementNotFoundException if no matching user was found
     */
    List<TransactionDTO> findUserTransactions(Integer id)
            throws ElementNotFoundException;
}
