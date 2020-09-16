package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Authority;
import com.paymybuddy.buddy.dto.AuthorityDTO;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * this interface provides methods that allow
 * conversion from AssociatedBankAccountInfo to AssociatedBankAccountInfoDTO
 * and vice versa
 */

public interface AuthorityConverter {

    /**
     * Convert Authority entity to AuthorityDTO.
     * @param authority entity to be converted
     * @return AuthorityDTO
     */
    AuthorityDTO authorityEntityToDTOConverter(Authority authority);

    /**
     * Convert a list of Authority entities to a list of AuthorityDTOs.
     * @param authorities a list of entities to be converted
     * @return a list of AuthorityDTOs
     */
    List<AuthorityDTO> authorityEntityListToDTOListConverter(
            List<Authority> authorities);

    /**
     * Convert AuthorityDTO to Authority entity.
     * @param authorityDTO DTO to be converted
     * @return Authority entity
     */
    Authority authorityDTOToEntityConverter(AuthorityDTO authorityDTO);

    /**
     * Convert a list of AuthorityDTOs to a list of Authority entities.
     * @param authorityDTOS list of DTOs to be converted
     * @return list of Authority entities
     */
    List<Authority> authorityDTOListToEntityListConverter(
            List<AuthorityDTO> authorityDTOS);
}
