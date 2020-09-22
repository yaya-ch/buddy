package com.paymybuddy.buddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.ContactDTO;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private ModelMapper mapper;

    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    private User setUser() {
        user = new User();
        String encoded = passwordEncoder.encode("mljdyefdj254");
        Set<ContactDTO> contacts = new HashSet<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("ROLE_ADMIN");
        userDTO.setCivility("MADAM");
        userDTO.setFirstName("girl");
        userDTO.setLastName("woman");
        userDTO.setEmail("girl@woman.com");
        userDTO.setPassword(encoded);
        userDTO.setBirthDate(LocalDate.of(1967, 12, 21));
        userDTO.setAddress("any address");
        userDTO.setCity("any city");
        userDTO.setZip("12345");
        userDTO.setPhone("2434343434");
        userDTO.setContacts(contacts);

        mapper.map(userDTO, user);
        return user;
    }

    @DisplayName("POST REQUEST: User is saved successfully")
    @Test
    void givenNewUser_whenSaveMethodIsCalled_thenUserShouldBePersistedToDataBase() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(new ObjectMapper().writeValueAsString(setUser()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Request on private '/user' service should redirect user to login form")
    @Test
    void givenGetRequestOnPrivateUserService_whenUserIsOffline_thenRequestShouldBeRedirected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().is3xxRedirection());
    }

    @DisplayName("Request on private '/admin' service should redirect user to login form")
    @Test
    void givenGetRequestOnPrivateAdminService_whenUserIsOffline_thenRequestShouldBeRedirected() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().is3xxRedirection());
    }

    @DisplayName("Admin with correct credentials logs successfully to account")
    @WithMockUser(username = "admin@admin.com", password = "test123", roles = "ADMIN")
    @Test
    void givenCorrectAdminCredentials_whenUserLogsToAccount_thenResponseShouldBeOk() throws Exception {
        String message = "<h1>Welcome admin</h1>";
        mockMvc.perform(MockMvcRequestBuilders.get("/admin")).andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @DisplayName("User with correct credentials logs successfully to account")
    @WithMockUser(username = "user@user.com", password = "test123", roles = "USER")
    @Test
    void givenCorrectUserCredentials_whenUserLogsToAccount_thenResponseShouldBeOk() throws Exception {
        String message = "<h1>Welcome user</h1>";
        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @DisplayName("Admin with correct credentials logs successfully to user account")
    @WithMockUser(username = "admin@admin.com", password = "test123", roles = "ADMIN")
    @Test
    void givenUserWithROLE_ADMIN_whenUserLogsToUserAccount_thenResponseShouldBeOk() throws Exception {
        String message = "<h1>Welcome user</h1>";
        mockMvc.perform(MockMvcRequestBuilders.get("/user")).andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @DisplayName("User with ROLE_USER cannot log to admin account")
    @WithMockUser(username = "user@user.com", password = "test123", roles = "USER")
    @Test
    void givenUserWithROLE_USER_whenUserLogsToAdminAccount_thenAccessShouldBeForbidden() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin"))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("Update an existing user successfully")
    @Test
    void givenAnExistingUser_whenUpdateUserIsCalled_thenUserCredentialsShouldBeUpdated() throws Exception {
        Optional<User> user1 = Optional.of(new User());
        when(userService.findById(anyInt())).thenReturn(user1);
        mapper.map(user1.get(), user);
        when(userService.updateUser(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/update/2")
                .content(new ObjectMapper().writeValueAsString(setUser()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @DisplayName("Update throws exception")
    @Test
    void givenNonExistingUser_whenUpdateUserIsCalled_thenExceptionShouldBeThrown() {
        UserController controller = new UserController(userService, userConverter, passwordEncoder, mapper);
        UserDTO userDTO = new UserDTO();
        when(userService.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        assertThrows(UsernameNotFoundException.class, () -> controller.updateUser(userDTO, anyInt()));
    }

    @DisplayName("Delete an existing user successfully")
    @Test
    void givenAnExistingUser_whenDeleteUserIsCalled_thenUserShouldBeDeleted() throws Exception {
        Optional<User> user1 = Optional.of(new User());
        when(userService.findById(anyInt())).thenReturn(user1);
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/1")).andExpect(status().isOk());
    }

    @DisplayName("Delete a user throws exception")
    @Test
    void givenNonExistingUser_whenDeleteUserIsCalled_thenExceptionShouldBeThrown() throws Exception {
        UserController controller = new UserController(userService, userConverter, passwordEncoder, mapper);
        when(userService.findById(anyInt())).thenReturn(Optional.ofNullable(user));
        assertThrows(UsernameNotFoundException.class, () -> controller.deleteUserById(1));
    }
}