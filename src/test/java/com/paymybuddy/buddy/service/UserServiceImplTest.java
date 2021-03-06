package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("service")
class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @AfterEach
    void tearDown() {
        userService = null;
    }

    @DisplayName("Save calls userRepository.save")
    @Test
    void save_shouldCallTheAppropriateMethodInPersonRepository() {
        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.save(user);
        verify(userRepository, times(1)).save(user);
    }

    @DisplayName("Save throws exception when matching email exists in db")
    @Test
    void givenExistingEmailAddress_whenCreatingNewAccount_ThenExceptionShouldBeThrown() {
        User user = new User();
        user.setEmail("email@mail.com");

        User user1 = new User();
        user1.setEmail("email@mail.com");

        when(userRepository.findByEmail("email@mail.com")).thenReturn(user);

        assertThrows(DataIntegrityViolationException.class, () -> userService.save(user1));
    }

    @Test
    void findById_shouldCallTheAppropriateMethodInUserRepository() {
        userService.findById(anyInt());
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    void findByEmail_shouldCallTheAppropriateMethodInUserRepository() {
        userService.findByEmail(anyString());
        verify(userRepository, times(1)).findByEmail(anyString());
    }

    @Test
    void updateUser_shouldCallTheAppropriateMethodInUserRepository() {
        User user = new User();
        userService.updateUser(user);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void deleteById_shouldCallTheAppropriateMethodInUserRepository() {
        userService.deleteById(anyInt());
        verify(userRepository, times(1)).deleteById(anyInt());
    }
}