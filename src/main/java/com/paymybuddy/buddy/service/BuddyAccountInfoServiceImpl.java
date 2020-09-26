package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author YAhia CHERIFI
 * This class implements the BuddyAccountInfoService
 * @see BuddyAccountInfoService
 */

@Service
public class BuddyAccountInfoServiceImpl implements BuddyAccountInfoService {

    /**
     * BuddyAccountInfoRepository to inject.
     */
    private final BuddyAccountInfoRepository buddyAccountInfoRepository;

    /**
     * Construction injection.
     * @param repository BuddyAccountInfoRepository
     */
    @Autowired
    public BuddyAccountInfoServiceImpl(
            final BuddyAccountInfoRepository repository) {
        this.buddyAccountInfoRepository = repository;
    }

    /**
     * @param id BuddyAccountInfo id
     * @return the found BuddyAccountInfo
     */
    @Override
    public Optional<BuddyAccountInfo> findById(final Integer id) {
        return buddyAccountInfoRepository.findById(id);
    }
}
