package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.dto.BuddyAccountInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yahia CHERIFI
 * A class that implements BuddyAccountInfoConverter interface
 * @see BuddyAccountInfoConverter
 */
@Component
public class BuddyAccountInfoConverterImpl
        implements BuddyAccountInfoConverter {

    /**
     * ModelMapper to be injected.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param modelMapper ModelMapper instance
     */
    public BuddyAccountInfoConverterImpl(
            final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * @param buddyAccountInfo entity to be converted
     * @return BuddyAccountInfoDTO
     */
    @Override
    public BuddyAccountInfoDTO buddyAccountInfoEntityToDTO(
            final BuddyAccountInfo buddyAccountInfo) {
        return mapper.map(buddyAccountInfo, BuddyAccountInfoDTO.class);
    }

    /**
     * @param buddyAccountInfos a list of entities to be converted
     * @return a list of BuddyAccountInfoDTOs
     */
    @Override
    public List<BuddyAccountInfoDTO> buddyAccountInfoEntityListToDTOList(
            final List<BuddyAccountInfo> buddyAccountInfos) {
        return buddyAccountInfos.stream()
                .map(this::buddyAccountInfoEntityToDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param buddyAccountInfoDTO entity to be converted
     * @return BuddyAccountInfo
     */
    @Override
    public BuddyAccountInfo buddyAccountInfoDTOToEntity(
            final BuddyAccountInfoDTO buddyAccountInfoDTO) {
        return mapper.map(buddyAccountInfoDTO, BuddyAccountInfo.class);
    }

    /**
     * @param buddyAccountInfoDTOList list of DTOs to be converted
     * @return a list of BuddyAccountInfo entities
     */
    @Override
    public List<BuddyAccountInfo> buddyAccountInfoDTOListToEntityList(
            final List<BuddyAccountInfoDTO> buddyAccountInfoDTOList) {
        return buddyAccountInfoDTOList.stream()
                .map(this::buddyAccountInfoDTOToEntity)
                .collect(Collectors.toList());
    }
}
