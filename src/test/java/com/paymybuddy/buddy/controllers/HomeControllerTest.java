package com.paymybuddy.buddy.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("GET REQUEST: OK '/' and '/buddy' return the correct message")
    @Test
    void homePage_shouldReturnCorrectMessage() throws Exception {
        String message = "<h1>Welcome on pay my buddy."
                +"\nThe easiest way to transfer your money</h1>";

        mockMvc.perform(get("/buddy"))
                .andExpect(status().isOk())
                .andExpect(content().string(message));

        mockMvc.perform(get("/"))
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