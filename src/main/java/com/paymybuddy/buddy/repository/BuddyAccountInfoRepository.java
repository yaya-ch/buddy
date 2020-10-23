package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interaction with the database.
 * it extends the JpaRepository interface
 */

@Repository
public interface BuddyAccountInfoRepository extends
        JpaRepository<BuddyAccountInfo, Integer> {

    /**
     * Update BuddyAccountInfo balance.
     * @param id BuddyAccountInfo id
     * @param updatedAccountBalance the new account balance that will replace
     *                              the previous one
     */
    @Transactional
    @Modifying
    @Query("UPDATE BuddyAccountInfo b"
            + " SET b.actualAccountBalance=:updatedAccountBalance"
            + " WHERE b.buddyAccountInfoId=:id")
    void updateActualAccountBalance(@Param("id")Integer id,
                                    @Param("updatedAccountBalance")
                                            Double updatedAccountBalance);

    /**
     * Update the previous account balance.
     * by replacing the current one by the amount
     * of money that a user had before the transaction
     * @param id the user's id
     * @param updatePreviousBalance the amount that will be set
     */
    @Transactional
    @Modifying
    @Query("UPDATE BuddyAccountInfo b"
            + " SET b.previousAccountBalance=:updatePreviousBalance"
            + " WHERE b.buddyAccountInfoId=:id")
    void updatePreviousAccountBalance(@Param("id") Integer id,
                                      @Param("updatePreviousBalance")
                                              Double updatePreviousBalance);
}
