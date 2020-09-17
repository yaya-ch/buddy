package com.paymybuddy.buddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.buddy.converters.UserConverter;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.ContactDTO;
import com.paymybuddy.buddy.dto.UserDTO;
import com.paymybuddy.buddy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter converter;

    @Mock
    private ModelMapper mapper;

    private User user;

    private User setUser() {
        user = new User();
        Set<ContactDTO> contacts = new HashSet<>();
        UserDTO userDTO = new UserDTO();
        userDTO.setRole("ADMIN");
        userDTO.setCivility("MADAM");
        userDTO.setFirstName("girl");
        userDTO.setLastName("woman");
        userDTO.setEmail("girl@woman.com");
        userDTO.setPassword("mljdye_fdj254");
        userDTO.setBirthDate(LocalDate.of(1967, 12, 21));
        userDTO.setAddress("any address");
        userDTO.setCity("any city");
        userDTO.setZip("12345");
        userDTO.setPhone("2434343434");
        userDTO.setContacts(contacts);

        mapper.map(userDTO, user);
        return user;
    }

    @DisplayName("POST request: OK. Saving a new user")
    @Test
    void givenNewUser_whenSaveMethodIsCalled_thenUserShouldBePersistedToDataBase() throws Exception {
        when(userService.save(any(User.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                .content(new ObjectMapper().writeValueAsString(setUser()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}