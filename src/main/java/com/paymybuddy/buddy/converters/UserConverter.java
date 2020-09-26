package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.ContactDTO;
import com.paymybuddy.buddy.dto.UserDTO;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * this interface provides methods that allow
 * conversion from User to UserDTO and ContactDTO
 * and vice versa
 */

public interface UserConverter {

    /**
     * Convert user entity to UserDTO.
     * @param user to be converted
     * @return UserDTO
     */
    UserDTO userEntityToUserDTO(User user);

    /**
     * Convert a list of User entities to a list of UserDTOs.
     * @param users list to be converted
     * @return a list of UserDTOs
     */
    List<UserDTO> userEntityListToUserDTOList(List<User> users);

    /**
     * Convert UserDTO to User Entity.
     * @param userDTO to be converted
     * @return User entity
     */
    User userDTOToUserEntity(UserDTO userDTO);

    /**
     * Convert a list of UserDTOs to a list of User entities.
     * @param userDTOS list to be converted
     * @return a list of user entities
     */
    List<User> userDTOListToUserEntityList(List<UserDTO> userDTOS);

    /**
     * Convert a user entity to ContactDTO.
     * @param user to be converted
     * @return ContactDTO
     */
    ContactDTO userEntityToContactDTO(User user);

    /**
     * Covert a list of User entities to a list of ContactDTOs.
     * @param users list to be converted
     * @return a list of ContactDTOs
     */
    List<ContactDTO> userEntityListToContactDTOList(List<User> users);

    /**
     * Convert ContactDTO to User entity.
     * @param contactDTO to be converted
     * @return User entity
     */
    User contactDTOToUserEntity(ContactDTO contactDTO);

    /**
     * Convert a list of ContactDTOs to a list of User entities.
     * @param contactDTOS list to be converted
     * @return a list of user entities
     */
    List<User> contactDTOListToUserEntityList(List<ContactDTO> contactDTOS);
}
