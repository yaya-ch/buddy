package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This interface contains abstract methods that provide
 * the logic to operated on the data sent to and from
 * the controllers and the repository layer
 */

public interface BuddyAccountInfoService {

    /**
     * Find BuddyAccountInfo by id.
     * @param id BuddyAccountInfo id
     * @return the found BuddyAccountInfo
     */
    Optional<BuddyAccountInfo> findById(Integer id);
}
