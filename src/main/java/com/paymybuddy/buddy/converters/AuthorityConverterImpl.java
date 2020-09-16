package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Authority;
import com.paymybuddy.buddy.dto.AuthorityDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yahia CHERIFI
 * A class that implements AuthorityConverter interface
 * @see AuthorityConverter
 */

@Component
public class AuthorityConverterImpl implements AuthorityConverter {

    /**
     * ModelMapper to be injected.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param modelMapper ModelMapper instance
     */
    @Autowired
    public AuthorityConverterImpl(final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * @param authority entity to be converted
     * @return AuthorityDTO
     */
    @Override
    public AuthorityDTO authorityEntityToDTOConverter(
            final Authority authority) {
        return mapper.map(authority, AuthorityDTO.class);
    }

    /**
     * @param authorities a list of entities to be converted
     * @return a list of AuthorityDTOs
     */
    @Override
    public List<AuthorityDTO> authorityEntityListToDTOListConverter(
            final List<Authority> authorities) {
        return authorities.stream()
                .map(this::authorityEntityToDTOConverter)
                .collect(Collectors.toList());
    }

    /**
     * @param authorityDTO DTO to be converted
     * @return Authority entity
     */
    @Override
    public Authority authorityDTOToEntityConverter(
            final AuthorityDTO authorityDTO) {
        return mapper.map(authorityDTO, Authority.class);
    }

    /**
     * @param authorityDTOS list of DTOs to be converted
     * @return list of Authority entities
     */
    @Override
    public List<Authority> authorityDTOListToEntityListConverter(
            final List<AuthorityDTO> authorityDTOS) {
        return authorityDTOS.stream()
                .map(this::authorityDTOToEntityConverter)
                .collect(Collectors.toList());
    }
}
