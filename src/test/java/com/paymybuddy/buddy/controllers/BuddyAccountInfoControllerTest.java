package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.BuddyAccountInfoConverter;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.service.BuddyAccountInfoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class BuddyAccountInfoControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    BuddyAccountInfoService service;

    @MockBean
    private BuddyAccountInfoConverter converter;

    private BuddyAccountInfo accountInfo;

    @BeforeEach
    void setUp() {
        accountInfo = new BuddyAccountInfo();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @DisplayName("Find by id returns matching account")
    @Test
    void givenExistingAccountId_whenFindByIdIsCalled_thenAccountShouldBeReturned() throws Exception {
        when(service.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(accountInfo));
        mockMvc.perform(MockMvcRequestBuilders.get("/buddyAccountInfo/getAccountInfo/1"))
                .andExpect(status().isOk());
    }

    @DisplayName("Find by id throws exception")
    @Test
    void givenNonExistingAccountId_whenFindByIdIsCalled_thenExceptionShouldBeThrown() {
        BuddyAccountInfoController controller = new BuddyAccountInfoController(service, converter);
        assertThrows(ElementNotFoundException.class, () -> controller.findById(1));
    }
}