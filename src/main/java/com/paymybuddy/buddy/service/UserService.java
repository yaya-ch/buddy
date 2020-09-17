package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.User;


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
}
