package com.paymybuddy.buddy.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class HomeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    private static final String message = "<h1>Welcome on pay my buddy."
            +"\nThe easiest way to transfer your money</h1>";

    @DisplayName("GET REQUEST: OK '/' return the correct message")
    @Test
    void homePage_shouldReturnCorrectMessage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @DisplayName("GET REQUEST: OK '/buddy' return the correct message")
    @Test
    void homePageBuddyPath_shouldReturnCorrectMessage() throws Exception {
        mockMvc.perform(get("/buddy"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));
    }

    @DisplayName("GET REQUEST: Wrong url returns 4XX error")
    @Test
    void givenWrongUrl_whenHomePageIsCalled_thenError4xxShouldBeReturned() throws Exception {
        mockMvc.perform(get("/bud"))
                .andExpect(status().is4xxClientError());
    }
}