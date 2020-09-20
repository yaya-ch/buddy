package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;
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

    @Mock
    private UserConverter userConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, userConverter);
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
}