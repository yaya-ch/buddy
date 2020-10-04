package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.repository.AssociatedBankAccountInfoRepository;
import com.paymybuddy.buddy.service.AssociatedBankAccountInfoServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Yahia CHERIFI
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayMyBuddyApplication.class)
public class AssociatedBankAccountInfoServiceImplIT {

    @Autowired
    private AssociatedBankAccountInfoServiceImpl associatedBankAccountInfoService;

    @Autowired
    private AssociatedBankAccountInfoRepository repository;

    private AssociatedBankAccountInfo associatedBankAccountInfo;

    private AssociatedBankAccountInfo setAssociatedBankAccountInfo() {
        associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setAssociatedBankAccountInfoId(1);
        associatedBankAccountInfo.setBankAccountHolderFirstName("user");
        associatedBankAccountInfo.setBankAccountHolderLastName("user");
        associatedBankAccountInfo.setIban("IBANIBAN123IBAN");
        associatedBankAccountInfo.setBic("BICBIC345");
        return associatedBankAccountInfo;
    }

    @DisplayName("Correct id returns correct associated bank account")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    public void givenCorrectId_whenFindByIdIsCalled_thenCorrectDataShouldBeReturned() {
        repository.save(setAssociatedBankAccountInfo());
        Optional<AssociatedBankAccountInfo> found = associatedBankAccountInfoService.findById(1);
        assertEquals("IBANIBAN123IBAN", found.get().getIban());
    }

    @DisplayName("Non existing id returns correct null")
    @Test
    public void givenNonExistingId_whenFindByIdIsCalled_thenNullBeReturned() {
        Optional<AssociatedBankAccountInfo> found = associatedBankAccountInfoService.findById(11);
        assertEquals(false, found.isPresent());
    }

}
