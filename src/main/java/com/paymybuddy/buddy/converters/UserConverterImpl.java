package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.ContactDTO;
import com.paymybuddy.buddy.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yahia CHERIFI
 * A class that implements UserConverter interface
 * @see UserConverter
 */

@Component
public class UserConverterImpl implements UserConverter {

    /**
     * ModelMapper to be injected.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param modelMapper modelMapper instance
     */
    public UserConverterImpl(final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * @param user to be converted
     * @return UserDTO
     */
    @Override
    public UserDTO userEntityToUserDTO(final User user) {
        return mapper.map(user, UserDTO.class);
    }

    /**
     * @param users list to be converted
     * @return a list of UserDTOs
     */
    @Override
    public List<UserDTO> userEntityListToUserDTOList(final List<User> users) {
        return users.stream()
                .map(this::userEntityToUserDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param userDTO to be converted
     * @return User entity
     */
    @Override
    public User userDTOToUserEntity(final UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    /**
     * @param userDTOS list to be converted
     * @return a list of user entities
     */
    @Override
    public List<User> userDTOListToUserEntityList(
            final List<UserDTO> userDTOS) {
        return userDTOS.stream()
                .map(this::userDTOToUserEntity)
                .collect(Collectors.toList());
    }

    /**
     * @param user to be converted
     * @return ContactDTO
     */
    @Override
    public ContactDTO userEntityToContactDTO(final User user) {
        return mapper.map(user, ContactDTO.class);
    }

    /**
     * @param users list to be converted
     * @return a list of ContactDTOs
     */
    @Override
    public List<ContactDTO> userEntityListToContactDTOList(
            final List<User> users) {
        return users.stream()
                .map(this::userEntityToContactDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param contactDTO to be converted
     * @return User entity
     */
    @Override
    public User contactDTOToUserEntity(final ContactDTO contactDTO) {
        return mapper.map(contactDTO, User.class);
    }

    /**
     * @param contactDTOS list to be converted
     * @return a list of user entities
     */
    @Override
    public List<User> contactDTOListToUserEntityList(
            final List<ContactDTO> contactDTOS) {
        return contactDTOS.stream()
                .map(this::contactDTOToUserEntity)
                .collect(Collectors.toList());
    }
}
