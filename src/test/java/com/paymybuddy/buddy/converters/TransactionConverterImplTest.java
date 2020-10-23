package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.BuddyAccountInfoTransactionsDTO;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("Converters")
class TransactionConverterImplTest {

    private TransactionConverter converter;

    private Transaction transaction;

    private TransactionDTO transactionDTO;

    @Mock
    private ModelMapper mapper;

    private Transaction setTransaction() {
        transaction = new Transaction();
        BuddyAccountInfo b = new BuddyAccountInfo();
        b.setBuddyAccountInfoId(1);
        transaction.setSender(b);
        transaction.setRecipient(new BuddyAccountInfo());
        transaction.setTransactionNature(TransactionNature.TO_CONTACTS);
        transaction.setAmount(150.0);
        transaction.setFee(10.0);
        transaction.setDescription("Birthday gift");
        transaction.setInitialTransactionStatusInfo(TransactionStatusInfo.TRANSACTION_IN_PROGRESS);
        transaction.setInitialTransactionStatusInfoDate(new Date());
        transaction.setFinalTransactionStatusInfo(TransactionStatusInfo.TRANSACTION_ACCEPTED);
        transaction.setFinalTransactionStatusInfoDate(new Date());
        return transaction;
    }

    private TransactionDTO setTransactionDTO() {
        transactionDTO = new TransactionDTO();
        BuddyAccountInfoTransactionsDTO b = new BuddyAccountInfoTransactionsDTO();
        b.setBuddyAccountInfoId(1);
        transactionDTO.setSender(b);
        transactionDTO.setRecipient(new BuddyAccountInfoTransactionsDTO());
        transactionDTO.setTransactionNature("BETWEEN_ACCOUNTS");
        transactionDTO.setAmount(200.0);
        transactionDTO.setFee(20.0);
        transactionDTO.setDescription("Your money");
        transactionDTO.setInitialTransactionStatusInfo("TRANSACTION_IN_PROGRESS");
        transactionDTO.setInitialTransactionStatusInfoDate(new Date());
        transactionDTO.setFinalTransactionStatusInfo("TRANSACTION_REJECTED");
        transactionDTO.setFinalTransactionStatusInfoDate(new Date());
        return transactionDTO;
    }

    @BeforeEach
    void setUp() {
        mapper = new ModelMapper();
        converter = new TransactionConverterImpl(mapper);
        setTransaction();
        setTransactionDTO();
    }

    @AfterEach
    void tearDown() {
        mapper = null;
        converter = null;
        transaction = null;
        transactionDTO = null;
    }

    @DisplayName("Convert Transaction entity to TransactionDTO")
    @Test
    void transactionEntityToTransactionDTO_shouldConvertEntityToDTO() {
        TransactionDTO transactionDTO1 = converter.transactionEntityToTransactionDTO(transaction);

        assertEquals(transactionDTO1.getSender().getBuddyAccountInfoId(), transaction.getSender().getBuddyAccountInfoId());
        assertEquals(transactionDTO1.getTransactionNature(), transaction.getTransactionNature().toString());
        assertEquals(transactionDTO1.getAmount(), transaction.getAmount());
        assertEquals(transactionDTO1.getFee(), transaction.getFee());
        assertEquals(transactionDTO1.getInitialTransactionStatusInfo(), transaction.getInitialTransactionStatusInfo().toString());
        assertEquals(transactionDTO1.getInitialTransactionStatusInfoDate(), transaction.getInitialTransactionStatusInfoDate());
        assertEquals(transactionDTO1.getFinalTransactionStatusInfo(), transaction.getFinalTransactionStatusInfo().toString());
        assertEquals(transactionDTO1.getFinalTransactionStatusInfoDate(), transaction.getFinalTransactionStatusInfoDate());
        assertEquals(transactionDTO1.getDescription(), transaction.getDescription());
    }

    @DisplayName("Convert Transaction entity list to TransactionDTO list")
    @Test
    void transactionEntityListToTransactionDTOList_shouldConvertEntityListToDTOList() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        List<TransactionDTO> transactionDTOList = converter
                .transactionEntityListToTransactionDTOList(transactions);

        assertEquals(transactionDTOList.size(), transactions.size());
    }

    @DisplayName("Convert TransactionDTO to Transaction entity")
    @Test
    void transactionDTOToTransactionEntity_shouldConvertDTOToEntity() {
        Transaction transaction1 = converter.transactionDTOToTransactionEntity(transactionDTO);

        assertEquals(transaction1.getTransactionNature().toString(), transactionDTO.getTransactionNature());
        assertEquals(transaction1.getAmount(), transactionDTO.getAmount());
        assertEquals(transaction1.getFee(), transactionDTO.getFee());
        assertEquals(transaction1.getDescription(), transactionDTO.getDescription());
        assertEquals(transaction1.getInitialTransactionStatusInfo().toString(), transactionDTO.getInitialTransactionStatusInfo());
        assertEquals(transaction1.getInitialTransactionStatusInfoDate(), transactionDTO.getInitialTransactionStatusInfoDate());
        assertEquals(transaction1.getFinalTransactionStatusInfo().toString(), transactionDTO.getFinalTransactionStatusInfo());
        assertEquals(transaction1.getFinalTransactionStatusInfoDate(), transactionDTO.getFinalTransactionStatusInfoDate());
    }

    @DisplayName("Convert TransactionDTO list to Transaction entity list")
    @Test
    void transactionDTOListToTransactionEntityList_shouldConvertDTOListToEntityList() {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        transactionDTOList.add(transactionDTO);

        List<Transaction> transactions = converter.transactionDTOListToTransactionEntityList(transactionDTOList);

        assertEquals(transactions.size(), transactionDTOList.size());
    }
}