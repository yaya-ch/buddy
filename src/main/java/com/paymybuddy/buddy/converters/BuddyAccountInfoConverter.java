package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.dto.BuddyAccountInfoDTO;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * this interface provides methods that allow
 * conversion from BuddyAccountInfo to BuddyAccountInfoDTO
 * and vice versa
 */

public interface BuddyAccountInfoConverter {

    /**
     * Convert BuddyAccountInfo entity to BuddyAccountInfoDTO.
     * @param buddyAccountInfo entity to be converted
     * @return BuddyAccountInfoDTO
     */
    BuddyAccountInfoDTO buddyAccountInfoEntityToDTO(
            BuddyAccountInfo buddyAccountInfo);

    /**
     * Convert a list of BuddyAccountInfo.
     * entities to a list of BuddyAccountInfoDTOs
     * @param buddyAccountInfos a list of entities to be converted
     * @return a list of BuddyAccountInfoDTOs
     */
    List<BuddyAccountInfoDTO> buddyAccountInfoEntityListToDTOList(
            List<BuddyAccountInfo> buddyAccountInfos);

    /**
     * Convert BuddyAccountInfoDTO to BuddyAccountInfo entity.
     * @param buddyAccountInfoDTO entity to be converted
     * @return BuddyAccountInfo
     */
    BuddyAccountInfo buddyAccountInfoDTOToEntity(
            BuddyAccountInfoDTO buddyAccountInfoDTO);

    /**
     * Convert a list of BuddyAccountInfoDTOs.
     * to a list of BuddyAccountInfo entities
     * @param buddyAccountInfoDTOList list of DTOs to be converted
     * @return a list of BuddyAccountInfo entities
     */
    List<BuddyAccountInfo> buddyAccountInfoDTOListToEntityList(
            List<BuddyAccountInfoDTO> buddyAccountInfoDTOList);
}
