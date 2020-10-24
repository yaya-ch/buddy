package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.dto.TransactionStatusDTO;
import com.paymybuddy.buddy.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class TransactionControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private TransactionService service;

    private TransactionDTO transaction;

    private TransactionStatusDTO transactionStatus;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        transaction = new TransactionDTO();
        transactionStatus = new TransactionStatusDTO();
        transaction.setAmount(100.5);
        transaction.setTransactionNature("TO_CONTACTS");
        transactionStatus.setInitialTransactionStatusInfo("TRANSACTION_IN_PROGRESS");
        transactionStatus.setInitialTransactionStatusInfoDate(new Date());
        transactionStatus.setFinalTransactionStatusInfo("TRANSACTION_ACCEPTED");
        transactionStatus.setFinalTransactionStatusInfoDate(new Date());
        transaction.setTransactionStatus(transactionStatus);
    }

    @DisplayName("Find by id returns correct transaction")
    @Test
    void givenExitingTransactionId_whenGetRequestIsSentByFindById_thenCorrectTransactionShouldBeReturned() throws Exception {
        when(service.findById(anyInt())).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/findById/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("LoadSenderTransactions returns a list of transactions")
    @Test
    void givenExitingUserId_whenGetRequestIsSentByFindUserTransactions_thenTransactionListShouldBeReturned() throws Exception {
        List<TransactionDTO> transactions = new ArrayList<>();
        when(service.loadSenderTransactions(anyInt())).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/findSenderTransactions/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("LoadRecipientTransactions returns a list of transactions")
    @Test
    void givenExitingUserId_whenGetRequestIsSentByLoadRecipientTransactions_thenTransactionListShouldBeReturned() throws Exception {
        List<TransactionDTO> transactions = new ArrayList<>();
        when(service.loadRecipientTransactions(anyInt())).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/findRecipientTransactions/1"))
                .andExpect(status().isOk());
    }
}