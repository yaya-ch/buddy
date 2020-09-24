package com.paymybuddy.buddy.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.buddy.converters.AssociatedBankAccountInfoConverter;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.service.AssociatedBankAccountInfoService;
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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Yahia CHERIFI
 */

@Tag("controllers")
@RunWith(SpringRunner.class)
@SpringBootTest
class AssociatedBankAccountInfoControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @MockBean
    private AssociatedBankAccountInfoService service;

    @MockBean
    private AssociatedBankAccountInfoConverter converter;

    @MockBean
    private ModelMapper mapper;

    private AssociatedBankAccountInfo associatedBankAccountInfo;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        associatedBankAccountInfo = new AssociatedBankAccountInfo();
        associatedBankAccountInfo.setAssociatedBankAccountInfoId(1);
        associatedBankAccountInfo.setBankAccountHolderFirstName("test");
        associatedBankAccountInfo.setBankAccountHolderLastName("user");
        associatedBankAccountInfo.setIban("AZERTYUIOP123");
        associatedBankAccountInfo.setBic("AZERTY123");
    }

    @DisplayName("Find by id returns the matching bank account information")
    @Test
    void givenExistingBankAccountId_whenFindByIdIsCalled_thenCorrectInformationShouldBeReturned() throws Exception {
        when(service.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(associatedBankAccountInfo));
        mockMvc.perform(MockMvcRequestBuilders.get("/bankAccountInfo/findById/1"))
                .andExpect(status().isOk());

    }

    @DisplayName("Find by id throw an exception")
    @Test
    void givenNonExistingBankAccountId_whenFindByIdIsCalled_thenExceptionShouldBeThrown() {
        AssociatedBankAccountInfoController controller = new AssociatedBankAccountInfoController(service, converter, mapper);
        when(service.findById(1)).thenReturn(java.util.Optional.ofNullable(associatedBankAccountInfo));
        assertThrows(ElementNotFoundException.class, () -> controller.findById(anyInt()));
    }

    @DisplayName("Update Bank account updates successfully")
    @Test
    void givenExistingBankAccountId_whenUpdateCalled_thenCorrectInformationShouldBeUpdated() throws Exception {
        Optional<AssociatedBankAccountInfo> associatedBankAccountInfo1 = Optional.of(new AssociatedBankAccountInfo());
        when(service.findById(anyInt())).thenReturn(associatedBankAccountInfo1);
        mapper.map(associatedBankAccountInfo1.get(), associatedBankAccountInfo);
        when(service.updateBankAccountInfo(any(AssociatedBankAccountInfo.class))).thenReturn(associatedBankAccountInfo);
        mockMvc.perform(MockMvcRequestBuilders.put("/bankAccountInfo/update/1")
                .content(new ObjectMapper().writeValueAsString(associatedBankAccountInfo))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @DisplayName("Update Bank throws an exception")
    @Test
    void givenNonExistingBankAccountId_whenUpdateCalled_thenExceptionShouldBeThrown() {
        AssociatedBankAccountInfoController controller = new AssociatedBankAccountInfoController(service, converter, mapper);
        assertThrows(ElementNotFoundException.class, () -> controller.updateBankAccountInfo(any(AssociatedBankAccountInfoDTO.class), 1));
    }
}