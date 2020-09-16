package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author YAhia CHERIFI
 * A class that implements AssociatedBankAccountInfoConverter interface
 * @see AssociatedBankAccountInfoConverter
 */

@Component
public class AssociatedBankAccountInfoConverterImpl
        implements AssociatedBankAccountInfoConverter {

    /**
     * ModelMapper to be injected.
     * used to map objects to each others
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param modelMapper ModelMapper
     */
    @Autowired
    public AssociatedBankAccountInfoConverterImpl(
            final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * @param associatedBankAccountInfo entity to be converted
     * @return AssociatedBankAccountInfoDTO
     */
    @Override
    public AssociatedBankAccountInfoDTO associatedBankAccountInfoToDTO(
            final AssociatedBankAccountInfo associatedBankAccountInfo) {
        return mapper.map(
                associatedBankAccountInfo, AssociatedBankAccountInfoDTO.class);
    }

    /**
     * @param associatedBankAccountInfos a list
     * of AssociatedBankAccountInfo entities
     * @return a list of AssociatedBankAccountInfoDTO
     */
    @Override
    public List<AssociatedBankAccountInfoDTO>
        associatedBankAccountInfoListToDTOList(
            final List<AssociatedBankAccountInfo> associatedBankAccountInfos) {
        return associatedBankAccountInfos.stream()
                .map(this::associatedBankAccountInfoToDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param associatedBankAccountInfoDTO DTO to be converted
     * @return AssociatedBankAccountInfo
     */
    @Override
    public AssociatedBankAccountInfo associatedBankAccountInfoDTOToEntity(
            final AssociatedBankAccountInfoDTO associatedBankAccountInfoDTO) {
        return mapper.map(
                associatedBankAccountInfoDTO, AssociatedBankAccountInfo.class);
    }

    /**
     * @param bankAccountInfoDTOS DTO list to be converted
     * @return a list of AssociatedBankAccountInfo
     */
    @Override
    public List<AssociatedBankAccountInfo>
        associatedBankAccountInfoDTOList2EntityList(
            final List<AssociatedBankAccountInfoDTO> bankAccountInfoDTOS) {
        return bankAccountInfoDTOS.stream()
                .map(this::associatedBankAccountInfoDTOToEntity)
                .collect(Collectors.toList());
    }
}
