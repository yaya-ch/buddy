package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interaction with the database.
 * it extends the JpaRepository interface
 */

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Integer> {

    /**
     * Find all sent transactions related a given buddy account.
     * @param id the buddy account id
     * @return a list of user related transactions
     */
    @Query(value =
            "SELECT * FROM transaction"
                    + " WHERE "
                    + "transaction.sender_buddy_account_info_id = ?",
            nativeQuery = true)
    List<Transaction> findSenderTransactions(Integer id);

    /**
     * Find all received transactions related a given buddy account.
     * @param id buddy account id
     * @return a list of transactions
     */
    @Query(value = "SELECT * FROM transaction"
            + " WHERE "
            + "transaction.recipient_buddy_account_info_id = ?",
            nativeQuery = true)
    List<Transaction> findRecipientTransactions(Integer id);
}
