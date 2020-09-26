package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Yahia CHERIFI
 */

@Tag("service")
class BuddyAccountInfoServiceImplTest {

    @Mock
    private BuddyAccountInfoRepository repository;

    private BuddyAccountInfoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new BuddyAccountInfoServiceImpl(repository);
    }

    @Test
    void findById_shouldCallTheAppropriateMethod() {
        service.findById(anyInt());
        verify(repository, times(1)).findById(anyInt());
    }
}