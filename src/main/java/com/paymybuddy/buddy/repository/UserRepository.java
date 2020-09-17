package com.paymybuddy.buddy.repository;

import com.paymybuddy.buddy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This interface provides methods that permit interaction with the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Retrieve users by email.
     * @param email the provided email
     * @return the matching user
     */
    Optional<User> findByEmail(String email);
}
