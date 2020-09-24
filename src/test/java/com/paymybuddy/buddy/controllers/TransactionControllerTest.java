package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.dto.TransactionDTO;
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

    @BeforeEach
    void setUp() {
        Date transactionDate = new Date();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        transaction = new TransactionDTO();
        transaction.setAmount(100.5);
        transaction.setTransactionStatusInfo("TRANSACTION_ACCEPTED");
        transaction.setTransactionNature("TO_CONTACTS");
        transaction.setTransactionDate(transactionDate);
    }

    @DisplayName("Find by id returns correct transaction")
    @Test
    void givenExitingTransactionId_whenGetRequestIsSentByFindById_thenCorrectTransactionShouldBeReturned() throws Exception {
        when(service.findById(anyInt())).thenReturn(transaction);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/findById/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("FindUserTransactions returns a list of transactions")
    @Test
    void givenExitingUserId_whenGetRequestIsSentByFindUserTransactions_thenTransactionListShouldBeReturned() throws Exception {
        List<TransactionDTO> transactions = new ArrayList<>();
        when(service.findUserTransactions(anyInt())).thenReturn(transactions);

        mockMvc.perform(MockMvcRequestBuilders.get("/transaction/findTransactions/1"))
                .andExpect(status().isOk());
    }
}