package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.service.MonetizingServiceImpl;
import com.paymybuddy.buddy.service.MoneyOpsServiceImpl;
import com.paymybuddy.buddy.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

    @Autowired
    private TransactionRepository transactionRepository;

    @DisplayName("Depositing money on account fails when no email matches")
    @Test
    public void givenNonExistingUserEmail_whenDepositMoneyOnAccountsCalled_thenExceptionShouldBeThrown() {
        assertThrows(ElementNotFoundException.class, () -> moneyOpsService
                .depositMoneyOnBuddyAccount("wrong@email.com", "IBANIBAN123456", 100.0));
    }

    @DisplayName("Depositing money on account fails when amount equals to 0 or higher than 1000")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenZeroOrMoreThanOneThousand_whenDepositMoneyOnAccountsCalled_thenExceptionShouldBeThrown() {
        assertThrows(MoneyOpsException.class, () -> moneyOpsService
                .depositMoneyOnBuddyAccount("user@user.com", "IBANIBAN123456", 1000.1));
    }

    @DisplayName("Existing user id return a list of available transactions")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenExistingUserEmail_whenDepositMoneyOnAccountsCalled_thenAccountCredited()
            throws ElementNotFoundException, MoneyOpsException {
        moneyOpsService.depositMoneyOnBuddyAccount("user@user.com", "IBANIBAN123IBAN", 100.0);
        User findUser = userService.findByEmail("user@user.com");
        Integer id = findUser.getUserId();
        Double balance = findUser.getBuddyAccountInfo().getActualAccountBalance();
        Double fee = monetizingService.transactionFee(100.0);
        Optional<Transaction> transaction = transactionRepository.findById(1);

        Double updatedBalance = (balance - fee) + 100.0;
        buddyAccountInfoRepository.updateActualAccountBalance(id, updatedBalance);
        buddyAccountInfoRepository.updatePreviousAccountBalance(id, balance);
        assertEquals(199.5, findUser.getBuddyAccountInfo().getActualAccountBalance());
        assertEquals(100.0, findUser.getBuddyAccountInfo().getPreviousAccountBalance());
        assertTrue(transaction.isPresent());
        assertEquals(1, transaction.get().getSender().getBuddyAccountInfoId());
        assertEquals(1, transaction.get().getRecipient().getBuddyAccountInfoId());
    }
}
