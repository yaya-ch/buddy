package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interaction with the database.
 * it extends the JpaRepository interface
 */

@Repository
public interface BuddyAccountInfoRepository extends
        JpaRepository<BuddyAccountInfo, Integer> {
}
