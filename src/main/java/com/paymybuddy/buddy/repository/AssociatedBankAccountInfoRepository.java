package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interactions with the database.
 * it extends the JpaRepository interface
 */

@Repository
public interface AssociatedBankAccountInfoRepository
        extends JpaRepository<AssociatedBankAccountInfo, Integer> {
}
