package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.service.MoneyOpsService;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @DisplayName("depositMoneyOnAccount returns correct message and calls correct service method")
    @Test
    void depositMoneyOnAccount_shouldReturnCorrectMessage() throws Exception {
        String message = "Your account has been credited successfully";
        mockMvc.perform(MockMvcRequestBuilders.put("/operations/deposit/email@email.com/10.1"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
        verify(moneyOpsService, times(1))
                .depositMoneyOnAccount("email@email.com", 10.1);
    }

    @DisplayName("transferMoneyToUsers returns correct message and calls correct service method")
    @Test
    void transferMoneyToUsers_shouldReturnCorrectMessage() throws Exception {
        String message = "Money transferred successfully";
        mockMvc.perform(MockMvcRequestBuilders
                .put("/operations/transfer/sender@email.com/receiver@email.com/100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
        verify(moneyOpsService, times(1))
                .sendMoneyToUsers("sender@email.com","receiver@email.com", 100.0);
    }

    @DisplayName("transferMoneyToBankAccount returns correct message and calls correct service method")
    @Test
    void transferMoneyToBankAccount_shouldReturnCorrectMessage() throws Exception {
        String message = "Money transferred successfully to your bank account";
        mockMvc.perform(MockMvcRequestBuilders
                .put("/operations/transferToBank/user@email.com/IBANIBANIBANIBAN/100.0"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));

        verify(moneyOpsService, times(1))
                .transferMoneyToBankAccount("user@email.com", "IBANIBANIBANIBAN", 100.0);
    }
}