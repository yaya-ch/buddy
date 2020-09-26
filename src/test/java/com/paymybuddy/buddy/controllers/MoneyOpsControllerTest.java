package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.service.MoneyOpsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class MoneyOpsControllerTest {


    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private MoneyOpsService moneyOpsService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String message = "Your account has been credited successfully";
    @Test
    void depositMoneyOnAccount_shouldReturnCorrectMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/operations/deposit/email@email.com/10.1"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }
}