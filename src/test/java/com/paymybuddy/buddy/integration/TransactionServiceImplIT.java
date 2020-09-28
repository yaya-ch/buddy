package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.enums.Civility;
import com.paymybuddy.buddy.enums.Role;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.service.TransactionServiceImpl;
import com.paymybuddy.buddy.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Autowired
    private UserServiceImpl userService;

    private User user;
    private BuddyAccountInfo buddyAccountInfo;
    private AssociatedBankAccountInfo associatedBankAccountInfo;
    private Transaction transaction;
    private Set<Transaction> transactions = new HashSet<>();
    private Set<User> contacts = new HashSet<>();

    private User setUser() {
        user = new User();
        user.setUserId(1);
        user.setCivility(Civility.MADAM);
        user.setFirstName("user");
        user.setLastName("user");
        user.setEmail("user@user.com");
        user.setPassword("test123");
        user.setRole(Role.ROLE_ADMIN);
        user.setBirthDate(LocalDate.of(1999, 01, 01));
        user.setAddress("any address");
        user.setCity("any city");
        user.setZip("12345");
        user.setPhone("123456789");
        user.setBuddyAccountInfo(setBuddyAccountInfo());
        user.setContacts(contacts);
        return user;
    }

    private BuddyAccountInfo setBuddyAccountInfo() {
        buddyAccountInfo = new BuddyAccountInfo();
        buddyAccountInfo.setBuddyAccountInfoId(1);
        buddyAccountInfo.setAccountBalance(100.0);
        transactions.add(setTransaction());
        buddyAccountInfo.setTransactions(transactions);
        buddyAccountInfo.setAssociatedBankAccountInfo(setAssociatedBankAccountInfo());
        return buddyAccountInfo;
    }

    private AssociatedBankAccountInfo setAssociatedBankAccountInfo() {
        associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setAssociatedBankAccountInfoId(1);
        associatedBankAccountInfo.setBankAccountHolderFirstName("user");
        associatedBankAccountInfo.setBankAccountHolderLastName("user");
        associatedBankAccountInfo.setIban("IBANIBAN123IBAN");
        associatedBankAccountInfo.setBic("BICBIC345");
        return associatedBankAccountInfo;
    }

    private Transaction setTransaction() {
        transaction = new Transaction();
        transaction.setTransactionId(1);
        transaction.setTransactionStatusInfo(TransactionStatusInfo.TRANSACTION_ACCEPTED);
        transaction.setAmount(10.0);
        transaction.setTransactionNature(TransactionNature.TO_CONTACTS);
        transaction.setTransactionDate(new Date());
        return transaction;
    }

    @DisplayName("Non existing transaction id returns null")
    @Test
    public void givenNonExistingTransactionId_whenFindByIdIsCalled_thenAnswerShouldBeNull() {
        Optional<Transaction> found = repository.findById(11);
        assertEquals(false, found.isPresent());
    }

    @DisplayName("Existing user id return a list of available transactions")
    @Test
    public void givenExistingUserId_whenFindUserTransactions_thenListShouldBeReturned()
            throws ElementNotFoundException {
        User persist = userService.save(setUser());
        List<TransactionDTO> transactions = transactionService.findUserTransactions(1);
        assertEquals(1, transactions.size());
    }
}
