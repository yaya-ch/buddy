package com.paymybuddy.buddy.security;

import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.enums.Role;
import com.paymybuddy.buddy.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Tag("security")
@SpringJUnitConfig(UserDetailsServiceImpl.class)
class UserDetailsServiceImplTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setRole(Role.ROLE_ADMIN);
        user.setFirstName("user");
        user.setLastName("user");
        user.setEmail("user@user.com");
        user.setPassword("encoded");
    }

    @AfterEach
    void tearDown() {
        user = null;
    }

    @DisplayName("LoadByUserName loads the right user")
    @Test
    void givenValidEmailAddress_whenLoadByUserNameCalled_thenCorrectUserCredentialsShouldBeLoaded() {
        when(userRepository.findByEmail("user@user.com")).thenReturn(user);

        UserDetails findUser = userDetailsService.loadUserByUsername("user@user.com");

        assertEquals(findUser.getUsername(), user.getEmail());
        assertEquals(findUser.getPassword(), user.getPassword());
        assertEquals(findUser.getAuthorities(),Arrays.stream(user.getRole().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList()));
        assertTrue(findUser.isAccountNonExpired());
        assertTrue(findUser.isAccountNonLocked());
        assertTrue(findUser.isCredentialsNonExpired());
        assertTrue(findUser.isEnabled());
        verify(userRepository, times(1)).findByEmail("user@user.com");
    }

    @DisplayName("LoadByUserName throws exception when no user found")
    @Test
    void givenWrongEmailAddress_whenLoadByUserNameCalled_thenExceptionShouldBeThrown() {
        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("wrong@email.com"));
    }
}