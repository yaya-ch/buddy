package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;


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
    private final UserConverter userConverter;

    /**
     * BcryptPassWordEncoder to inject.
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * ModelMapper to inject.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param service UserService
     * @param converter UserConverter
     * @param encoder BcryptPassWordEncoder
     * @param modelMapper modelMapper
     */
    @Autowired
    public UserController(final UserService service,
                          final UserConverter converter,
                          final PasswordEncoder encoder,
                          final ModelMapper modelMapper) {
        this.userService = service;
        this.userConverter = converter;
        this.passwordEncoder = encoder;
        this.mapper = modelMapper;
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

    /**
     * Update user.
     * @param userDTO groups the information to update
     * @param id the user's id
     * @return success message
     */
    @PutMapping("/update/{id}")
    public String updateUser(@RequestBody final UserDTO userDTO,
                             @PathVariable final Integer id) {
        LOGGER.info("Trying to update user {}", id);
        Optional<User> userToUpdate = userService.findById(id);
        if (userToUpdate.isPresent()) {
            String encodedPassWord =
                    passwordEncoder.encode(userDTO.getPassword());
            userDTO.setPassword(encodedPassWord);
            mapper.map(userDTO, userToUpdate.get());
            userService.updateUser(userToUpdate.get());
            LOGGER.info("User updated successfully {}", id);
            return "User updated successfully";
        } else {
            LOGGER.error("failed to update user with id {}."
                    + " No matching id was found", id);
            throw new BadCredentialsException(
                    "Bad credentials. Check your input please");
        }
    }

    /**
     * Delete a user by id.
     * @param id the user's id
     * @return success message if deleted
     */
    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable final Integer id) {
        LOGGER.debug("Delete request sent from the user controller."
                + " Trying to delete user with id {}", id);
        Optional<User> checkForExistingUser = userService.findById(id);
        if (checkForExistingUser.isPresent()) {
            LOGGER.info("User with id {} deleted successfully.", id);
            userService.deleteById(id);
            return "User deleted successfully " + id;
        } else {
            LOGGER.error("Failed to delete user with id: {}."
                    + " No matching Id was found", id);
            throw new BadCredentialsException(
                    "Bad credentials. Check your input please");
        }
    }
}
