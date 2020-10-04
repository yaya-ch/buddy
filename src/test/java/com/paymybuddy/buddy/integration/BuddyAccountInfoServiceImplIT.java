package com.paymybuddy.buddy.integration;


import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.service.BuddyAccountInfoServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yahia CHERIFI
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayMyBuddyApplication.class)
public class BuddyAccountInfoServiceImplIT {

    @Autowired
    private BuddyAccountInfoServiceImpl buddyAccountInfoService;

    @Autowired
    private BuddyAccountInfoRepository repository;

    private BuddyAccountInfo buddyAccountInfo;
    private Set<Transaction> transactions = new HashSet<>();
    private AssociatedBankAccountInfo associatedBankAccountInfo;

    private BuddyAccountInfo setBuddyAccountInfo() {
        buddyAccountInfo = new BuddyAccountInfo();
        buddyAccountInfo.setBuddyAccountInfoId(1);
        buddyAccountInfo.setAccountBalance(100.0);
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

    @DisplayName("Correct BuddyAccountInfo id returns the accountInfo")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void givenExistingBuddyAccountId_whenFindByIdIsCalled_thenCorrectDataShouldBeReturned() {
        repository.save(setBuddyAccountInfo());
        Optional<BuddyAccountInfo> find = buddyAccountInfoService.findById(1);
        assertEquals(100.0, find.get().getAccountBalance());
    }

    @DisplayName("Non existing BuddyAccountId returns null")
    @Test
    public void givenNonExistingBuddyAccountId_whenFindByIdIsCalled_thenNullBeReturned() {
        Optional<BuddyAccountInfo> find = buddyAccountInfoService.findById(11);
        assertEquals(false, find.isPresent());
    }
}
