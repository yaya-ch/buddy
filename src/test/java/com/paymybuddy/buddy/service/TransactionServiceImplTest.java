package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.TransactionConverter;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("service")
class TransactionServiceImplTest {

    private TransactionService transactionService;

    @Mock
    private TransactionRepository repository;

    @Mock
    private UserRepository userRepository;

    @Mock
    TransactionConverter converter;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(repository, userRepository, converter);
        transaction = new Transaction();
    }

    @DisplayName("Find transaction by id calls the findId in transaction repo")
    @Test
    void givenExistingTransactionId_whenFindByIdIsCalled_thenTransactionShouldBeReturned() throws ElementNotFoundException {
        when(repository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(transaction));
        transactionService.findById(1);
        verify(repository, times(1)).findById(anyInt());
    }

    @DisplayName("Find transaction by id throws exception")
    @Test
    void givenNonExistingTransactionId_whenFindByIdIsCalled_thenExceptionShouldBeRethrown() {
        when(repository.findById(1)).thenReturn(java.util.Optional.ofNullable(transaction));
       assertThrows(ElementNotFoundException.class, () -> transactionService.findById(anyInt()));
    }

    @DisplayName("Get user transactions returns calls the right method in transaction repo")
    @Test
    void givenExistingUserId_whenGetUserTransactionsIsCalled_thenTransactionListShouldBeReturned() throws ElementNotFoundException {
        List<Transaction> transactions = new ArrayList<>();
        Optional<User> user = Optional.of(new User());
        when(repository.findUserTransactions(anyInt())).thenReturn(transactions);
        when(userRepository.findById(anyInt())).thenReturn(user);
        transactionService.findUserTransactions(1);
        verify(repository, times(1)).findUserTransactions(1);
    }

    @DisplayName("Get user transactions throws exception")
    @Test
    void givenNonExistingUserId_whenGetUserTransactionsIsCalled_thenExceptionShouldBeThrown() throws ElementNotFoundException {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThrows(ElementNotFoundException.class, () -> transactionService.findUserTransactions(anyInt()));
    }
}