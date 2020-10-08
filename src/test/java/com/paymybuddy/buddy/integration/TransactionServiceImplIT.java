package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.service.TransactionServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * @author Yahia CHERIFI
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayMyBuddyApplication.class)
public class TransactionServiceImplIT {

    @Autowired
    private TransactionServiceImpl transactionService;

    @Autowired
    private TransactionRepository repository;

    @DisplayName("Non existing transaction id returns null")
    @Test
    public void givenNonExistingTransactionId_whenFindByIdIsCalled_thenAnswerShouldBeNull() {
        Optional<Transaction> findTransaction = repository.findById(11);
        assertFalse(findTransaction.isPresent());
    }

    @DisplayName("Existing user id return a list of available transactions")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenExistingUserId_whenFindUserTransactions_thenListShouldBeReturned()
            throws ElementNotFoundException {
        List<TransactionDTO> transactions = transactionService.findUserTransactions(1);
        assertEquals(0, transactions.size());
    }
}
