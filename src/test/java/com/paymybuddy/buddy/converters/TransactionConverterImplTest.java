package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.TransactionStatus;
import com.paymybuddy.buddy.dto.BuddyAccountInfoTransactionsDTO;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.dto.TransactionStatusDTO;
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
        transaction.setTransactionStatus(transactionStatus());
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
        transactionDTO.setTransactionStatus(transactionStatusDTO());
        return transactionDTO;
    }

    private TransactionStatus transactionStatus() {
        TransactionStatus transactionStatus = new TransactionStatus(
                TransactionStatusInfo.TRANSACTION_IN_PROGRESS,
                new Date(),
                TransactionStatusInfo.TRANSACTION_ACCEPTED,
                new Date());
        return transactionStatus;

    }
    private TransactionStatusDTO transactionStatusDTO() {
        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();
        transactionStatusDTO.setInitialTransactionStatusInfo("TRANSACTION_IN_PROGRESS");
        transactionStatusDTO.setInitialTransactionStatusInfoDate(new Date());
        transactionStatusDTO.setFinalTransactionStatusInfo("TRANSACTION_REJECTED");
        transactionStatusDTO.setFinalTransactionStatusInfoDate(new Date());
        return transactionStatusDTO;
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
        assertEquals(transactionDTO1.getTransactionStatus().getInitialTransactionStatusInfo(),
                transaction.getTransactionStatus().getInitialTransactionStatusInfo().toString());
        assertEquals(transactionDTO1.getTransactionStatus().getInitialTransactionStatusInfoDate(),
                transaction.getTransactionStatus().getInitialTransactionStatusInfoDate());
        assertEquals(transactionDTO1.getTransactionStatus().getFinalTransactionStatusInfo(),
                transaction.getTransactionStatus().getFinalTransactionStatusInfo().toString());
        assertEquals(transactionDTO1.getTransactionStatus().getFinalTransactionStatusInfoDate(),
                transaction.getTransactionStatus().getFinalTransactionStatusInfoDate());
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
        assertEquals(transaction1.getTransactionStatus().getInitialTransactionStatusInfo().toString(),
                transactionDTO.getTransactionStatus().getInitialTransactionStatusInfo());
        assertEquals(transaction1.getTransactionStatus().getInitialTransactionStatusInfoDate(),
                transactionDTO.getTransactionStatus().getInitialTransactionStatusInfoDate());
        assertEquals(transaction1.getTransactionStatus().getFinalTransactionStatusInfo().toString(),
                transactionDTO.getTransactionStatus().getFinalTransactionStatusInfo());
        assertEquals(transaction1.getTransactionStatus().getFinalTransactionStatusInfoDate(),
                transactionDTO.getTransactionStatus().getFinalTransactionStatusInfoDate());
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