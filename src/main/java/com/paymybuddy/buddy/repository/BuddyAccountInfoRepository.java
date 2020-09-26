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
     * @param id
     * @param updatedAccountBalance
     */
    @Transactional
    @Modifying
    @Query("UPDATE BuddyAccountInfo b"
            + " SET b.accountBalance=:updatedAccountBalance"
            + " WHERE b.buddyAccountInfoId=:id")
    void updateBalance(@Param("id")Integer id,
                       @Param("updatedAccountBalance")
                               Double updatedAccountBalance);
}
