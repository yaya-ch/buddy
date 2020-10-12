package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.service.AssociatedBankAccountInfoServiceImpl;
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
public class AssociatedBankAccountInfoServiceImplIT {

    @Autowired
    private AssociatedBankAccountInfoServiceImpl associatedBankAccountInfoService;

    @DisplayName("Correct id returns correct associated bank account")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenCorrectId_whenFindByIdIsCalled_thenCorrectDataShouldBeReturned() {
        Optional<AssociatedBankAccountInfo> findAssociatedBankAccountInf =
                associatedBankAccountInfoService.findById(1);
        assertTrue(findAssociatedBankAccountInf.isPresent());
        assertEquals("IBANIBAN123IBAN", findAssociatedBankAccountInf.get().getIban());
    }

    @DisplayName("Non existing id returns correct null")
    @Test
    public void givenNonExistingId_whenFindByIdIsCalled_thenNullBeReturned() {
        Optional<AssociatedBankAccountInfo> findAssociatedBankAccountInf =
                associatedBankAccountInfoService.findById(11);
        assertFalse(findAssociatedBankAccountInf.isPresent());
    }

}
