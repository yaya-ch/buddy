package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Transaction;
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
        Date transactionDate = new Date();
        transaction.setTransactionDate(transactionDate);
        transaction.setTransactionNature(TransactionNature.TO_CONTACTS);
        transaction.setAmount(150.0);
        transaction.setFee(10.0);
        transaction.setTransactionStatusInfo(TransactionStatusInfo.TRANSACTION_ACCEPTED);
        return transaction;
    }

    private TransactionDTO setTransactionDTO() {
        transactionDTO = new TransactionDTO();
        Date transactionDTODate = new Date();
        transactionDTO.setTransactionDate(transactionDTODate);
        transactionDTO.setTransactionNature("BETWEEN_ACCOUNTS");
        transactionDTO.setAmount(200.0);
        transactionDTO.setFee(20.0);
        transactionDTO.setTransactionStatusInfo("TRANSACTION_REJECTED");
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

        assertEquals(transactionDTO1.getTransactionDate(), transaction.getTransactionDate());
        assertEquals(transactionDTO1.getTransactionNature(), transaction.getTransactionNature().toString());
        assertEquals(transactionDTO1.getAmount(), transaction.getAmount());
        assertEquals(transactionDTO1.getFee(), transaction.getFee());
        assertEquals(transactionDTO1.getTransactionStatusInfo(), transaction.getTransactionStatusInfo().toString());
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

        assertEquals(transaction1.getTransactionDate(), transactionDTO.getTransactionDate());
        assertEquals(transaction1.getTransactionNature().toString(), transactionDTO.getTransactionNature());
        assertEquals(transaction1.getAmount(), transactionDTO.getAmount());
        assertEquals(transaction1.getFee(), transactionDTO.getFee());
        assertEquals(transaction1.getTransactionStatusInfo().toString(), transactionDTO.getTransactionStatusInfo());
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