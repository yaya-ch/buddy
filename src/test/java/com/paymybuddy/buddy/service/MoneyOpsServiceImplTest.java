package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Tag("service")
class MoneyOpsServiceImplTest {

    private MoneyOpsServiceImpl moneyOpsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BuddyAccountInfoRepository buddyAccountInfoRepository;

    private MonetizingService monetizingService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        monetizingService = new MonetizingServiceImpl();
        moneyOpsService = new MoneyOpsServiceImpl(
                userRepository,
                buddyAccountInfoRepository,
                monetizingService,
                transactionRepository);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("Deposit money successfully")
    @Test
    void givenCorrectData_whenUserDepositMoneyOnAccount_thenOperationShouldBeSuccessful()
            throws ElementNotFoundException, MoneyOpsException {
        User user = new User();
        BuddyAccountInfo accountInfo = new BuddyAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        user.setUserId(1);
        user.setEmail("email@email.com");
        accountInfo.setAccountBalance(0.0);
        accountInfo.setTransactions(transactions);
        user.setBuddyAccountInfo(accountInfo);
        Double deposit = 100.0;
        Double fee = monetizingService.transactionFee(deposit);
        Double balance = user.getBuddyAccountInfo().getAccountBalance();

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        moneyOpsService.depositMoneyOnAccount(user.getEmail(), deposit);
        Double newBalance = balance + (deposit - fee);
        accountInfo.setAccountBalance(newBalance);

        assertEquals(newBalance, user.getBuddyAccountInfo().getAccountBalance());
        assertEquals(1, transactions.size());
    }

    @DisplayName("Deposit 0 or more than 1000 on account throws exception")
    @Test
    void givenZeroOrMoreThan1000Buddies_whenUserDepositMoneyOnAccount_thenExceptionShouldBeThrown() {
        User user = new User();
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        buddyAccountInfo.setTransactions(transactions);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        user.setBuddyAccountInfo(buddyAccountInfo);
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnAccount("email@email.com", 0.0));
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnAccount("email@email.com", 1000.01));
    }

    @DisplayName("Deposit money on invalid user's email throws exception")
    @Test
    void givenIncorrectEmail_whenUserDepositMoneyOnAccount_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .depositMoneyOnAccount("email@email.com", 0.0));
    }

    @DisplayName("Send money to users successfully")
    @Test
    void givenCorrectData_whenTransferringMoneyToUser_thenOperationShouldBeSuccessful()
            throws MoneyOpsException, ElementNotFoundException {
        User sender = new User();
        sender.setEmail("sender@email.com");
        User receiver = new User();
        receiver.setEmail("receiver@email.com");
        BuddyAccountInfo senderAccountInfo = new BuddyAccountInfo();
        BuddyAccountInfo receiverAccountInfo = new BuddyAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        senderAccountInfo.setAccountBalance(1000.0);
        senderAccountInfo.setTransactions(transactions);
        receiverAccountInfo.setAccountBalance(50.0);
        receiverAccountInfo.setTransactions(transactions);
        sender.setBuddyAccountInfo(senderAccountInfo);
        receiver.setBuddyAccountInfo(receiverAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        System.out.println(senderAccountInfo.getAccountBalance());
        System.out.println(receiverAccountInfo.getAccountBalance());

        moneyOpsService.sendMoneyToUsers(sender.getEmail(), receiver.getEmail(), 10.0);

    }

    @DisplayName("Send money to invalid user's email throws exception")
    @Test
    void givenNonExistingReceiverEmail_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail("sender@email.com")).thenReturn(any(User.class));
        when(userRepository.findByEmail("receiver@email.com")).thenReturn(null);

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com", 0.0));
    }

    @DisplayName("Send money from an invalid user's email throws exception")
    @Test
    void givenWrongSenderEmail_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail("sender@email.com")).thenReturn(null);
        when(userRepository.findByEmail("receiver@email.com")).thenReturn(any(User.class));

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com", 0.0));
    }

    @DisplayName("Sending 0 or more than 1000 buddies to a user throws exception")
    @Test
    void givenZeroOrMoreThan1000Buddies_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        buddyAccountInfo.setTransactions(transactions);
        User sender = new User();
        sender.setBuddyAccountInfo(buddyAccountInfo);
        User receiver = new User();
        receiver.setBuddyAccountInfo(buddyAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com", 0.0));
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com", 1000.01));
    }

    @DisplayName("Insufficient balance throws exception")
    @Test
    void givenInsufficientAccountBalance_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        User sender = new User();
        User receiver = new User();
        BuddyAccountInfo accountInfo = new BuddyAccountInfo();
        Set<Transaction> transactions = new HashSet<>();
        accountInfo.setAccountBalance(0.0);
        accountInfo.setTransactions(transactions);
        sender.setBuddyAccountInfo(accountInfo);
        receiver.setBuddyAccountInfo(accountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com", 100.0));
    }
}