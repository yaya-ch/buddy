package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * this interface provides methods that allow
 * conversion from AssociatedBankAccountInfo to AssociatedBankAccountInfoDTO
 */

public interface AssociatedBankAccountInfoConverter {

    /**
     * Convert AssociatedBankAccountInfo entity to AssociatedBankAccountInfoDTO.
     * @param associatedBankAccountInfo entity to be converted
     * @return AssociatedBankAccountInfoDTO
     */
    AssociatedBankAccountInfoDTO associatedBankAccountInfoToDTO(
            AssociatedBankAccountInfo associatedBankAccountInfo);

    /**
     * Convert a list of AssociatedBankAccountInfo.
     * entities to AssociatedBankAccountInfoDTO
     * @param associatedBankAccountInfos a list
     * of AssociatedBankAccountInfo entities
     * @return a list of AssociatedBankAccountInfoDTO
     */
    List<AssociatedBankAccountInfoDTO> associatedBankAccountInfoListToDTOList(
            List<AssociatedBankAccountInfo> associatedBankAccountInfos);

    /**
     * Convert AssociatedBankAccountInfoDTO to entity.
     * @param associatedBankAccountInfoDTO DTO to be converted
     * @return AssociatedBankAccountInfo
     */
    AssociatedBankAccountInfo associatedBankAccountInfoDTOToEntity(
            AssociatedBankAccountInfoDTO associatedBankAccountInfoDTO);

    /**
     * Convert a list of AssociatedBankAccountInfoDTO to a list of entities.
     * @param associatedBankAccountInfoDTOS DTO list to be converted
     * @return a list of AssociatedBankAccountInfo
     */
    List<AssociatedBankAccountInfo> associatedBankAccountInfoDTOList2EntityList(
            List<AssociatedBankAccountInfoDTO> associatedBankAccountInfoDTOS);
}
