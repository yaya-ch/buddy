package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.enums.Civility;
import com.paymybuddy.buddy.enums.Role;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.service.MonetizingServiceImpl;
import com.paymybuddy.buddy.service.MoneyOpsServiceImpl;
import com.paymybuddy.buddy.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Yahia CHERIFI
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayMyBuddyApplication.class)
public class MoneyOpsServiceImplIT {

    @Autowired
    private MoneyOpsServiceImpl moneyOpsService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private MonetizingServiceImpl monetizingService;

    @Autowired
    private BuddyAccountInfoRepository buddyAccountInfoRepository;

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

    @DisplayName("Depositing money on account fails when no email matches")
    @Test
    public void givenNonExistingUserEmail_whenDepositMoneyOnAccountsCalled_thenExceptionShouldBeThrown() {
        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .depositMoneyOnAccount("wrong@email.com", 100.0));
    }

    @DisplayName("Depositing money on account fails when amount equals to 0 or higher than 1000")
    @Test
    public void givenZeroOrMoreThanOneThousand_whenDepositMoneyOnAccountsCalled_thenExceptionShouldBeThrown() {
        userService.save(setUser());
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnAccount("user@user.com", 1000.1));
    }

    @DisplayName("Existing user id return a list of available transactions")
    @Test
    public void givenExistingUserEmail_whenDepositMoneyOnAccountsCalled_thenAccountCredited()
            throws ElementNotFoundException, MoneyOpsException {
        userService.save(setUser());
        moneyOpsService.depositMoneyOnAccount("user@user.com", 100.0);
        User find = userService.findByEmail("user@user.com");
        Integer id = find.getUserId();
        Double balance = find.getBuddyAccountInfo().getAccountBalance();
        Double fee = monetizingService.transactionFee(100.0);

        Double updatedBalance = (balance - fee) + 100.0;
        buddyAccountInfoRepository.updateBalance(id, updatedBalance);
        assertEquals(199.5, find.getBuddyAccountInfo().getAccountBalance());
    }
}
