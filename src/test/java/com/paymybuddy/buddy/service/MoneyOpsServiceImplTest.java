package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Yahia CHERIFI
 */

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
        AssociatedBankAccountInfo bankAccountInfo = new AssociatedBankAccountInfo();
        bankAccountInfo.setIban("IBANIBAN123456");
        user.setUserId(1);
        user.setEmail("email@email.com");
        accountInfo.setActualAccountBalance(0.0);
        accountInfo.setAssociatedBankAccountInfo(bankAccountInfo);
        user.setBuddyAccountInfo(accountInfo);
        Double deposit = 100.0;
        Double fee = monetizingService.transactionFee(deposit);
        Double balance = user.getBuddyAccountInfo().getActualAccountBalance();

        when(userRepository.findByEmail(anyString())).thenReturn(user);
        moneyOpsService.depositMoneyOnBuddyAccount(user.getEmail(), "IBANIBAN123456", deposit, "success");
        Double newBalance = balance + (deposit - fee);
        buddyAccountInfoRepository.updateActualAccountBalance(1, newBalance);
        accountInfo.setActualAccountBalance(newBalance);

        assertEquals(newBalance, user.getBuddyAccountInfo().getActualAccountBalance());
    }

    @DisplayName("Deposit 0 or more than 1000 on account throws exception")
    @Test
    void givenZeroOrMoreThan1000Buddies_whenUserDepositMoneyOnAccount_thenExceptionShouldBeThrown() {
        User user = new User();
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        AssociatedBankAccountInfo bankAccountInfo = new AssociatedBankAccountInfo();
        bankAccountInfo.setIban("IBANIBAN123456");
        buddyAccountInfo.setAssociatedBankAccountInfo(bankAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        user.setBuddyAccountInfo(buddyAccountInfo);
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnBuddyAccount("email@email.com", "IBANIBAN123456",
                        0.0, "exception"));
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnBuddyAccount("email@email.com", "IBANIBAN123456",
                        1000.01, "exception"));
    }

    @DisplayName("Deposit money on invalid user's email throws exception")
    @Test
    void givenIncorrectEmail_whenUserDepositMoneyOnAccount_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .depositMoneyOnBuddyAccount("email@email.com", "IBANIBAN123456",
                        0.0, "exception"));
    }

    @DisplayName("Send money to users successfully")
    @Test
    void givenCorrectData_whenTransferringMoneyToUser_thenOperationShouldBeSuccessful()
            throws MoneyOpsException, ElementNotFoundException {
        User sender = new User();
        sender.setUserId(1);
        sender.setEmail("sender@email.com");
        User receiver = new User();
        receiver.setUserId(2);
        receiver.setEmail("receiver@email.com");
        BuddyAccountInfo senderAccountInfo = new BuddyAccountInfo();
        BuddyAccountInfo receiverAccountInfo = new BuddyAccountInfo();
        senderAccountInfo.setActualAccountBalance(1000.0);
        receiverAccountInfo.setActualAccountBalance(50.0);
        sender.setBuddyAccountInfo(senderAccountInfo);
        receiver.setBuddyAccountInfo(receiverAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        Double fee = monetizingService.transactionFee(10.0);
        Double newSenderBalance = 1000.0 - 10.0 - fee;
        Double newRecipientBalance = 50.0 + 10.0;
        moneyOpsService.sendMoneyToUsers(sender.getEmail(), receiver.getEmail(), 10.0, "success");
        sender.getBuddyAccountInfo().setActualAccountBalance(newSenderBalance);
        receiver.getBuddyAccountInfo().setActualAccountBalance(newRecipientBalance);
        buddyAccountInfoRepository.updateActualAccountBalance(sender.getUserId(), newSenderBalance);
        buddyAccountInfoRepository.updateActualAccountBalance(receiver.getUserId(), 10.1);

        assertEquals(989.95, sender.getBuddyAccountInfo().getActualAccountBalance());
        assertEquals(60, receiver.getBuddyAccountInfo().getActualAccountBalance());
    }

    @DisplayName("Send money TO invalid user's email throws exception")
    @Test
    void givenNonExistingReceiverEmail_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail("sender@email.com")).thenReturn(any(User.class));
        when(userRepository.findByEmail("receiver@email.com")).thenReturn(null);

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com",
                        0.0, "exception"));
    }

    @DisplayName("Send money FROM an invalid user's email throws exception")
    @Test
    void givenWrongSenderEmail_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail("sender@email.com")).thenReturn(null);
        when(userRepository.findByEmail("receiver@email.com")).thenReturn(any(User.class));

        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com",
                        0.0, "exception"));
    }

    @DisplayName("Sending 0 or more than 1000 buddies to a user throws exception")
    @Test
    void givenZeroOrMoreThan1000Buddies_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        User sender = new User();
        sender.setBuddyAccountInfo(buddyAccountInfo);
        User receiver = new User();
        receiver.setBuddyAccountInfo(buddyAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com",
                        0.0, "exception"));
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com",
                        1000.01, "exception"));
    }

    @DisplayName("Insufficient balance throws exception")
    @Test
    void givenInsufficientAccountBalance_whenTransferringMoneyToUser_thenExceptionShouldBeThrown() {
        User sender = new User();
        User receiver = new User();
        BuddyAccountInfo accountInfo = new BuddyAccountInfo();
        accountInfo.setActualAccountBalance(0.0);
        sender.setBuddyAccountInfo(accountInfo);
        receiver.setBuddyAccountInfo(accountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(sender);
        when(userRepository.findByEmail(anyString())).thenReturn(receiver);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .sendMoneyToUsers("sender@email.com", "receiver@email.com",
                        100.0, "exception"));
    }

    @DisplayName("Transfer money to bank account successfully")
    @Test
    void givenCorrectInformation_whenTransferMoneyToBankAccountIsCalled_thenOperationShouldBeSuccessful()
            throws ElementNotFoundException, MoneyOpsException {
        User user = new User();
        user.setUserId(1);
        user.setEmail("correct@email.com");
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        AssociatedBankAccountInfo associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setIban("AZERTYUIOP123");
        buddyAccountInfo.setActualAccountBalance(100.0);
        buddyAccountInfo.setAssociatedBankAccountInfo(associatedBankAccountInfo);
        user.setBuddyAccountInfo(buddyAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        moneyOpsService.transferMoneyToBankAccount("correct@email.com", "AZERTYUIOP123",
                10.0, "accepted");
        Double fee = monetizingService.transactionFee(10.0);
        Double update = (100.0 - fee - 10.0);
        buddyAccountInfoRepository.updateActualAccountBalance(1, update);
        user.getBuddyAccountInfo().setActualAccountBalance(update);
        assertEquals(89.95, user.getBuddyAccountInfo().getActualAccountBalance());
    }

    @DisplayName("Transfer money to bank throws exception when user's email is wrong")
    @Test
    void givenNonExistingUserCredentials_whenTransferMoneyToBankAccountIsCalled_thenExceptionShouldBeThrown() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .transferMoneyToBankAccount("wrong@email.com", "AZERTYUIOP123",
                        10.0, "exception"));
    }

    @DisplayName("Transfer money to bank throws exception when user's iban is wrong")
    @Test
    void givenWrongUserIBAN_whenTransferMoneyToBankAccountIsCalled_thenExceptionShouldBeThrown() {
        User user = new User();
        user.setEmail("correct@email.com");
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        AssociatedBankAccountInfo associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setIban("AZERTYUIOP123");
        buddyAccountInfo.setAssociatedBankAccountInfo(associatedBankAccountInfo);
        user.setBuddyAccountInfo(buddyAccountInfo);
        when(userRepository.findByEmail(anyString())).thenReturn(user);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .transferMoneyToBankAccount("correct@email.com", "WRONGIBAN",
                        233.3, "exception"));
    }

    @DisplayName("Transfer money to bank throws exception when user's email is wrong")
    @Test
    void givenInsufficientAccountBalance_whenTransferMoneyToBankAccountIsCalled_thenExceptionShouldBeThrown() {
        User user = new User();
        user.setEmail("correct@email.com");
        BuddyAccountInfo buddyAccountInfo = new BuddyAccountInfo();
        AssociatedBankAccountInfo associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setIban("AZERTYUIOP123");
        buddyAccountInfo.setActualAccountBalance(100.0);
        buddyAccountInfo.setAssociatedBankAccountInfo(associatedBankAccountInfo);
        user.setBuddyAccountInfo(buddyAccountInfo);

        when(userRepository.findByEmail(anyString())).thenReturn(user);

        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .transferMoneyToBankAccount("correct@email.com", "AZERTYUIOP123",
                        99.6, "exception"));
    }
}