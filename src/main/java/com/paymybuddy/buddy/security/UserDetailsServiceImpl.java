package com.paymybuddy.buddy.security;

import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Yahia CHERIFI
 */

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(UserDetailsServiceImpl.class);
    /**
     * UserRepository to inject.
     */
    private UserRepository userRepository;

    /**
     * Constructor injection.
     * @param repository user repository
     */
    @Autowired
    public UserDetailsServiceImpl(final UserRepository repository) {
        this.userRepository = repository;
    }

    /**
     * This method allows loading user by their email addresses.
     * @param email the user's email
     * @return the user
     * @throws UsernameNotFoundException if no user was found
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            LOGGER.error("Failed to load user."
                    + " No matching email found in database: {}.", email);
            throw new UsernameNotFoundException("No matching user found."
                   + " Try another email address.");
        } else {
            LOGGER.info("User loaded successfully. {}", email);
            return new CustomUserDetails(user);
        }
    }
}
