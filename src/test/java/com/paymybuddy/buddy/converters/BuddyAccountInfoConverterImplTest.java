package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;
import com.paymybuddy.buddy.dto.BuddyAccountInfoDTO;
import com.paymybuddy.buddy.dto.TransactionDTO;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia CHERIFI
 */
@Tag("Converters")
class BuddyAccountInfoConverterImplTest {

    private BuddyAccountInfoConverter converter;

    private BuddyAccountInfo buddyAccountInfo;

    private BuddyAccountInfoDTO buddyAccountInfoDTO;

    @Mock
    private ModelMapper mapper;

    private BuddyAccountInfo setBuddyAccountInfo() {
        AssociatedBankAccountInfo associatedBankAccountInfo = new AssociatedBankAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        buddyAccountInfo = new BuddyAccountInfo();
        buddyAccountInfo.setActualAccountBalance(1200.67);
        buddyAccountInfo.setAssociatedBankAccountInfo(associatedBankAccountInfo);
        buddyAccountInfo.setTransactions(transactions);
        return buddyAccountInfo;
    }

    private BuddyAccountInfoDTO setBuddyAccountInfoDTO() {
        AssociatedBankAccountInfoDTO associatedBankAccountInfoDTO = new AssociatedBankAccountInfoDTO();
        Set<TransactionDTO> transactionDTOS = new HashSet<>();
        buddyAccountInfoDTO = new BuddyAccountInfoDTO();
        buddyAccountInfoDTO.setActualAccountBalance(150.0);
        buddyAccountInfoDTO.setAssociatedBankAccountInfo(associatedBankAccountInfoDTO);
        buddyAccountInfoDTO.setTransactions(transactionDTOS);
        return buddyAccountInfoDTO;
    }

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        converter = new BuddyAccountInfoConverterImpl(mapper);
        setBuddyAccountInfo();
        setBuddyAccountInfoDTO();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        converter = null;
        buddyAccountInfo = null;
        buddyAccountInfoDTO = null;
    }

    @DisplayName("Convert BuddyAccountInfo entity to BuddyAccountInfoDTO")
    @Test
    void buddyAccountInfoEntityToDTO_shouldConvertEntityToDTO() {
        BuddyAccountInfoDTO buddyAccountInfoDTO1 = converter.buddyAccountInfoEntityToDTO(buddyAccountInfo);

        assertEquals(buddyAccountInfoDTO1.getActualAccountBalance(), buddyAccountInfo.getActualAccountBalance());
        assertEquals(buddyAccountInfoDTO1.getTransactions().size(), buddyAccountInfo.getTransactions().size());
    }

    @DisplayName("Convert BuddyAccountInfo entity list to BuddyAccountInfoDTO list")
    @Test
    void buddyAccountInfoEntityListToDTOList_shouldConvertEntityListToDTOList() {
        List<BuddyAccountInfo> buddyAccountInfos = new ArrayList<>();
        buddyAccountInfos.add(buddyAccountInfo);

        List<BuddyAccountInfoDTO> buddyAccountInfoDTOS =
                converter.buddyAccountInfoEntityListToDTOList(buddyAccountInfos);

        assertEquals(buddyAccountInfoDTOS.size(), buddyAccountInfos.size());
    }

    @DisplayName("Convert BuddyAccountInfoDTO to BuddyAccountInfo entity")
    @Test
    void buddyAccountInfoDTOToEntity_shouldConvertDTOToEntity() {
        BuddyAccountInfo buddyAccountInfo1 = converter.buddyAccountInfoDTOToEntity(buddyAccountInfoDTO);

        assertEquals(buddyAccountInfo1.getActualAccountBalance(), buddyAccountInfoDTO.getActualAccountBalance());
        assertEquals(buddyAccountInfo1.getTransactions().size(), buddyAccountInfoDTO.getTransactions().size());
    }

    @DisplayName("Convert BuddyAccountInfoDTO list to BuddyAccountInfo entity list")
    @Test
    void buddyAccountInfoDTOListToEntityList_shouldConvertDTOListToEntityList() {
        List<BuddyAccountInfoDTO> buddyAccountInfoDTOS = new ArrayList<>();
        buddyAccountInfoDTOS.add(buddyAccountInfoDTO);

        List<BuddyAccountInfo> buddyAccountInfos =
                converter.buddyAccountInfoDTOListToEntityList(buddyAccountInfoDTOS);

        assertEquals(buddyAccountInfos.size(), buddyAccountInfoDTOS.size());
    }
}