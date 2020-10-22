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
     * Find all user's transactions.
     * @param id the buddy account id
     * @return a list of user related transactions
     */
    @Query(value =
            "select * from transaction"
                    + " where"
                    + " transaction.buddy_account_info_id = ?",
            nativeQuery = true)
    List<Transaction> findUserTransactions(Integer id);
}
