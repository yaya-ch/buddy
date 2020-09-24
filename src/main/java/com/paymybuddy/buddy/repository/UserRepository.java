package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interaction with the database.
 * it extends the JpaRepository interface
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieve users by email.
     * @param email the provided email
     * @return the matching user
     */
    User findByEmail(String email);
}
