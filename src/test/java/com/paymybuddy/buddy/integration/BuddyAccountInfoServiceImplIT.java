package com.paymybuddy.buddy.integration;


import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.service.BuddyAccountInfoServiceImpl;
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
public class BuddyAccountInfoServiceImplIT {

    @Autowired
    private BuddyAccountInfoServiceImpl buddyAccountInfoService;

    @DisplayName("Correct BuddyAccountInfo id returns the accountInfo")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenExistingBuddyAccountId_whenFindByIdIsCalled_thenCorrectDataShouldBeReturned() {
        Optional<BuddyAccountInfo> findUser = buddyAccountInfoService.findById(1);
        assertTrue(findUser.isPresent());
        assertEquals(100.0, findUser.get().getActualAccountBalance());
    }

    @DisplayName("Non existing BuddyAccountId returns null")
    @Test
    public void givenNonExistingBuddyAccountId_whenFindByIdIsCalled_thenNullBeReturned() {
        Optional<BuddyAccountInfo> findUser = buddyAccountInfoService.findById(11);
        assertFalse(findUser.isPresent());
    }
}
