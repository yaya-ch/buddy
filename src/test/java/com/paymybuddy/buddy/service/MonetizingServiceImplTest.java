package com.paymybuddy.buddy.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author YAhia CHERIFI
 */

@Tag("service")
class MonetizingServiceImplTest {

    private MonetizingService monetizingService;
    @BeforeEach
    void setUp() {
        monetizingService = new MonetizingServiceImpl();
    }

    @Test
    void givenNumber_whenFeeIsCalculated_thenCorrectResultShouldBeReturned() {
        Double amount = 100.0;

        Double transactionFee = monetizingService.transactionFee(amount);

        assertEquals(0.5, transactionFee);
    }
}