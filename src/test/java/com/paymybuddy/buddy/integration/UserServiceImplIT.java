package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.User;
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
public class UserServiceImplIT {

    @Autowired
    private UserServiceImpl userService;

    @DisplayName("Non existing emails return null")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Test
    public void givenNonExistingEmailAddress_whenFindByEmailIsCalled_thenAnswerShouldBeNull() {
        User findUser = userService.findByEmail("wrong@mail.com");
        assertNull(findUser);
    }

    @DisplayName("Existing email returns correct user")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenCorrectEmailAddress_whenFindByEmailIsCalled_thenCorrectUserShouldBeReturned() {
        User findUser = userService.findByEmail("user@user.com");
        assertEquals(findUser.getEmail(), "user@user.com");
        assertEquals(findUser.getLastName(), "user");
        assertEquals(findUser.getFirstName(), "user");
    }

    @DisplayName("Correct id return correct user")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenCorrectUserId_whenFindByIdIsCalled_thenCorrectUserShouldBeReturned() {
        Optional<User> findUser = userService.findById(1);
        assertTrue(findUser.isPresent());
        assertEquals(findUser.get().getEmail(), "user@user.com");
        assertEquals(findUser.get().getLastName(), "user");
        assertEquals(findUser.get().getFirstName(), "user");
    }

    @DisplayName("Incorrect id return correct null")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenNonExistingUserId_whenFindByIdIsCalled_thenCorrectUserShouldBeReturned() {
        Optional<User> findUser = userService.findById(11);
        assertFalse(findUser.isPresent());
    }

    @DisplayName("Correct id delete correct user")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    @Sql(scripts = {"/sqlScriptForITs/payMyBuddyForITScript.sql"})
    @Test
    public void givenCorrectUserId_whenDeleteByIdIsCalled_thenUserShouldBeDeleted() {
        userService.deleteById(1);
        User findUser = userService.findByEmail("user@user.com");
        assertNull(findUser);
    }
}
