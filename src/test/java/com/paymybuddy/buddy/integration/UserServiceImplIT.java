package com.paymybuddy.buddy.integration;

import com.paymybuddy.buddy.PayMyBuddyApplication;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.enums.Civility;
import com.paymybuddy.buddy.enums.Role;
import com.paymybuddy.buddy.repository.UserRepository;
import com.paymybuddy.buddy.service.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author Yahia CHERIFI
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PayMyBuddyApplication.class)
public class UserServiceImplIT {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository repository;

    private User user;
    private BuddyAccountInfo buddyAccountInfo;
    private AssociatedBankAccountInfo associatedBankAccountInfo;
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

    @DisplayName("Non existing emails return null")
    @Test
    public void givenNonExistingEmailAddress_whenFindByEmailIsCalled_thenAnswerShouldBeNull() {
        User found = userService.findByEmail("wrong@mail.com");
        assertNull(found);
    }

    @DisplayName("Existing email returns correct user")
    @Test
    public void givenCorrectEmailAddress_whenFindByEmailIsCalled_thenCorrectUserShouldBeReturned() {
        User persist = repository.save(setUser());
        User found = userService.findByEmail("user@user.com");
        assertEquals(found.getEmail(), persist.getEmail());
        assertEquals(found.getLastName(), persist.getLastName());
        assertEquals(found.getFirstName(), persist.getFirstName());
    }

    @DisplayName("Correct id return correct user")
    @Test
    public void givenCorrectUserId_whenFindByIdIsCalled_thenCorrectUserShouldBeReturned() {
        User persist = repository.save(setUser());
        Optional<User> found = userService.findById(1);
        assertEquals(found.get().getEmail(), persist.getEmail());
        assertEquals(found.get().getLastName(), persist.getLastName());
        assertEquals(found.get().getFirstName(), persist.getFirstName());
    }

    @DisplayName("Incorrect id return correct null")
    @Test
    public void givenNonExistingUserId_whenFindByIdIsCalled_thenCorrectUserShouldBeReturned() {
        Optional<User> found = userService.findById(11);
        assertEquals(false, found.isPresent());
    }

    @DisplayName("Correct id delete correct user")
    @Test
    public void givenCorrectUserId_whenDeleteByIdIsCalled_thenUserShouldBeDeleted() {
        userService.save(setUser());
        userService.deleteById(1);
        User found = userService.findByEmail("user@user.com");
        assertNull(found);
    }
}
