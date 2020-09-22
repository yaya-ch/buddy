package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This class implements the UserService interface
 * @see UserService
 */

@Service
public class UserServiceImpl implements UserService {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(UserServiceImpl.class);

    /**
     * UserRepository to be injected.
     */
    private final UserRepository userRepository;

    /**
     * UserConverter to be injected.
     */
    private final UserConverter userConverter;

    /**
     * Constructor injection.
     * @param repository injecting UserRepository
     * @param converter injecting UserConverter
     */
    @Autowired
    public UserServiceImpl(final UserRepository repository,
                           final UserConverter converter) {
        this.userRepository = repository;
        this.userConverter = converter;
    }

    /**
     * @param id the provided user's id.
     * @return a call to the UserRepository's method findById()
     */
    @Override
    public Optional<User> findById(final Integer id) {
        return userRepository.findById(id);
    }

    /**
     * @param email the provided email
     * @return a call to the UserRepository's method findByEmail()
     */
    @Override
    public User findByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * @param user to be saved
     * @return a call to the userRepository method save
     */
    @Override
    public User save(final User user) {
        User checkForExistingUser =
                userRepository.findByEmail(user.getEmail());
        if (checkForExistingUser != null) {
            LOGGER.error("Failed to create account for {}.", user.getEmail());
            throw new DataIntegrityViolationException(
                    "Failed to create a new account."
                            + " Email already exists in database "
                            + user.getEmail());
        }
        LOGGER.info("New account created for {}.", user.getEmail());
        return userRepository.save(user);
    }

    /**
     * @param userToUpdate the new user's PII
     */
    @Override
    public User updateUser(final User userToUpdate) {
        return userRepository.save(userToUpdate);
    }

    /**
     * @param id the provided user's id
     */
    @Override
    public void deleteById(final Integer id) {
        userRepository.deleteById(id);
    }
}
