package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.repository.AssociatedBankAccountInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Yahia CHERIFI
 */

@Tag("service")
class AssociatedBankAccountInfoServiceImplTest {

    private AssociatedBankAccountInfoServiceImpl service;

    @Mock
    private AssociatedBankAccountInfoRepository repository;

    private AssociatedBankAccountInfo bankAccountInfo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new AssociatedBankAccountInfoServiceImpl(repository);
        bankAccountInfo = new AssociatedBankAccountInfo();
    }

    @DisplayName("Find by id calls the correct method")
    @Test
    void findById_shouldCallTheAppropriateMethod() {
        when(repository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(bankAccountInfo));
        service.findById(1);
        verify(repository, times(1)).findById(1);
    }

    @DisplayName("Update calls the correct method")
    @Test
    void update_shouldCallTheAppropriateMethod() {
        when(repository.save(any(AssociatedBankAccountInfo.class))).thenReturn(bankAccountInfo);
        service.updateBankAccountInfo(bankAccountInfo);
        verify(repository, times(1)).save(bankAccountInfo);
    }
}