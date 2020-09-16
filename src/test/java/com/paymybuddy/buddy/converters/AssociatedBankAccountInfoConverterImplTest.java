package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("Converters")
class AssociatedBankAccountInfoConverterImplTest {

    private AssociatedBankAccountInfoConverter converter;

    private AssociatedBankAccountInfo bankAccountInfo;

    private AssociatedBankAccountInfoDTO accountInfoDTO;

    @Mock
    private ModelMapper mapper;

    private AssociatedBankAccountInfo setBankAccountInfo() {
        bankAccountInfo = new AssociatedBankAccountInfo();
        bankAccountInfo.setBankAccountHolderFirstName("Spring");
        bankAccountInfo.setBankAccountHolderLastName("Boot");
        bankAccountInfo.setIban("WE2154534252789");
        bankAccountInfo.setBic("BICBIC6543");
        return bankAccountInfo;
    }

    private AssociatedBankAccountInfoDTO setAccountInfoDTO() {
        accountInfoDTO = new AssociatedBankAccountInfoDTO();
        accountInfoDTO.setBankAccountHolderFirstName("any");
        accountInfoDTO.setBankAccountHolderLastName("any");
        accountInfoDTO.setIban("IBAN123456789");
        accountInfoDTO.setBic("BIC98765");
        return accountInfoDTO;
    }

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        converter = new AssociatedBankAccountInfoConverterImpl(mapper);
        setBankAccountInfo();
        setAccountInfoDTO();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        converter = null;
        bankAccountInfo = null;
    }

    @DisplayName("Convert AssociatedBankAccountInfo to AssociatedBankAccountInfoDTO")
    @Test
    void associatedBankAccountInfoToDTO_shouldConvertEntityToDTO() {
        AssociatedBankAccountInfoDTO accountInfoDTO = converter.associatedBankAccountInfoToDTO(bankAccountInfo);

        assertEquals(accountInfoDTO.getBankAccountHolderFirstName(), bankAccountInfo.getBankAccountHolderFirstName());
        assertEquals(accountInfoDTO.getBankAccountHolderLastName(), bankAccountInfo.getBankAccountHolderLastName());
        assertEquals(accountInfoDTO.getBic(), bankAccountInfo.getBic());
        assertEquals(accountInfoDTO.getIban(), bankAccountInfo.getIban());
    }

    @DisplayName("Convert AssociatedBankAccountInfo list to AssociatedBankAccountInfoDTO list")
    @Test
    void associatedBankAccountInfoListToDTOList_shouldConvertEntityListToDTOList() {
        List<AssociatedBankAccountInfo> bankAccountInfoList = new ArrayList<>();
        bankAccountInfoList.add(bankAccountInfo);

        List<AssociatedBankAccountInfoDTO> accountInfoDTOList =
                converter.associatedBankAccountInfoListToDTOList(bankAccountInfoList);

        assertEquals(accountInfoDTOList.size(), bankAccountInfoList.size());
    }

    @DisplayName("Convert AssociatedBankAccountInfoDTO to AssociatedBankAccountInfo")
    @Test
    void associatedBankAccountInfoDTOToEntity_shouldConvertDTOToEntity() {
        AssociatedBankAccountInfo accountInfo = converter.associatedBankAccountInfoDTOToEntity(accountInfoDTO);

        assertEquals(accountInfo.getBankAccountHolderFirstName(), accountInfoDTO.getBankAccountHolderFirstName());
        assertEquals(accountInfo.getBankAccountHolderLastName(), accountInfoDTO.getBankAccountHolderLastName());
        assertEquals(accountInfo.getIban(), accountInfoDTO.getIban());
        assertEquals(accountInfo.getBic(), accountInfoDTO.getBic());
    }

    @DisplayName("Convert AssociatedBankAccountInfoDTO list to AssociatedBankAccountInfo list")
    @Test
    void associatedBankAccountInfoDTOList2EntityList_shouldConvertDTOListToEntityList() {
        List<AssociatedBankAccountInfoDTO> accountInfoDTOS = new ArrayList<>();
        accountInfoDTOS.add(accountInfoDTO);

        List<AssociatedBankAccountInfo> accountInfoList =
                converter.associatedBankAccountInfoDTOList2EntityList(accountInfoDTOS);

        assertEquals(accountInfoList.size(), accountInfoDTOS.size());
    }
}