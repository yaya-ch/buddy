package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Constructor injection.
     * @param service UserService
     * @param converter UserConverter
     */
    @Autowired
    public UserController(final UserService service,
                          final UserConverter converter) {
        this.userService = service;
        this.userConverter = converter;
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
        User userToPersist = userConverter.userDTOToUserEntity(userDTO);
        userService.save(userToPersist);
        return "Your account has been created. Use Your credentials to login";
    }
}
