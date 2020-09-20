package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Yahia CHERIFI
 * This controllers exposes different user related endoints
 */

@RestController
public class UserController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(UserController.class);

    /**
     * UserService to inject.
     */
    private final UserService userService;

    /**
     * UserConverter to be injected.
     * Will be used to convert entities to DTOs and vice versa
     */
    private UserConverter userConverter;

    /**
     * BcryptPassWordEncoder to inject.
     */
    private PasswordEncoder passwordEncoder;

    /**
     * Constructor injection.
     * @param service UserService
     * @param converter UserConverter
     * @param encoder BcryptPassWordEncoder
     */
    @Autowired
    public UserController(final UserService service,
                          final UserConverter converter,
                          final PasswordEncoder encoder) {
        this.userService = service;
        this.userConverter = converter;
        this.passwordEncoder = encoder;
    }

    /**
     * Post request: used to save new users to db.
     * @param userDTO UserDTO to be saved
     * @return message if account created
     */
    @PostMapping("/signup")
    public String save(@RequestBody final UserDTO userDTO) {
        LOGGER.info("Post request sent from the user controller."
                + " Save new user: {}", userDTO.getEmail());
        String encodedPassWord = passwordEncoder.encode(userDTO.getPassword());
        userDTO.setPassword(encodedPassWord);
        User userToPersist = userConverter.userDTOToUserEntity(userDTO);
        userService.save(userToPersist);
        return "Your account has been created. Use Your credentials to login";
    }

    /**
     * This methods allows users to login to their accounts.
     * @return a welcome message(html page)
     */
    @GetMapping("/user")
    public String userLogin() {
        LOGGER.debug("Get Request sent from the user login");
        return "<h1>Welcome user</h1>";
    }

    /**
     * This methods allows admins to login to their accounts.
     * @return a welcome message(html page)
     */
    @GetMapping("/admin")
    public String adminLogin() {
        LOGGER.debug("Get Request sent from the admin login");
        return "<h1>Welcome admin</h1>";
    }
}
