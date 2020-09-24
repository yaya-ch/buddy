package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.User;

import java.util.Optional;


/**
 * @author Yahia CHERIFI
 * This interface contains abstract methods that provide
 * the logic to operated on the data sent to and from
 * the controllers and the repository layer
 */

public interface UserService {

    /**
     * Save a new user to db.
     * @param user to be saved
     * @return the saved person
     */
    User save(User user);

    /**
     * Update user related information.
     * @param userToUpdate gathers the new user's information
     * @return a call to the user repo layer
     */
    User updateUser(User userToUpdate);

    /**
     * Find user by id.
     * @param id the provided user's id.
     * @return the user if found
     * or exception if no matching user was found
     */
    Optional<User> findById(Integer id);

    /**
     * Find user by email address.
     * @param email the provided email
     * @return the user if found
     * or an exception if no matching user's email was found
     */
    User findByEmail(String email);

    /**
     * Delete a user by id.
     * @param id the provided user's id
     */
    void deleteById(Integer id);
}
