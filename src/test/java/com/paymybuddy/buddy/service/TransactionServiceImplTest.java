package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.TransactionConverter;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private TransactionConverter converter;

    @Mock
    private BuddyAccountInfoRepository buddyRepository;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionServiceImpl(repository, converter, buddyRepository);
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
    void givenExistingUserId_whenLoadSenderTransactionsIsCalled_thenTransactionListShouldBeReturned() throws ElementNotFoundException {
        Optional<BuddyAccountInfo> buddy = Optional.of(new BuddyAccountInfo());
        when(buddyRepository.findById(anyInt())).thenReturn(buddy);
        transactionService.loadSenderTransactions(1);
        verify(repository, times(1)).findSenderTransactions(1);
    }

    @DisplayName("Get user transactions throws exception")
    @Test
    void givenNonExistingUserId_whenLoadSenderTransactionsIsCalled_thenExceptionShouldBeThrown() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThrows(ElementNotFoundException.class, () -> transactionService.loadSenderTransactions(anyInt()));
    }

    @DisplayName("Get user transactions returns calls the right method in transaction repo")
    @Test
    void givenExistingUserId_whenLoadRecipientTransactionsIsCalled_thenTransactionListShouldBeReturned() throws ElementNotFoundException {
        Optional<BuddyAccountInfo> buddy = Optional.of(new BuddyAccountInfo());
        when(buddyRepository.findById(anyInt())).thenReturn(buddy);
        transactionService.loadRecipientTransactions(1);
        verify(repository, times(1)).findRecipientTransactions(1);
    }

    @DisplayName("Get user transactions throws exception")
    @Test
    void givenNonExistingUserId_whenLoadRecipientTransactionsIsCalled_thenExceptionShouldBeThrown() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertThrows(ElementNotFoundException.class, () -> transactionService.loadRecipientTransactions(anyInt()));
    }
}