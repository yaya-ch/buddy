package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.repository.AssociatedBankAccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This class implements AssociatedBankAccountInfoService
 * @see AssociatedBankAccountInfoService
 */

@Service
public class AssociatedBankAccountInfoServiceImpl
        implements AssociatedBankAccountInfoService {

    /**
     * AssociatedBankAccountInfoRepository to inject.
     */
    private final AssociatedBankAccountInfoRepository bankAccountInfoRepository;

    /**
     * Constructor injection.
     * @param repository bankAccountInfoRepository
     */
    @Autowired
    public AssociatedBankAccountInfoServiceImpl(
            final AssociatedBankAccountInfoRepository repository) {
        this.bankAccountInfoRepository = repository;
    }

    /**
     * Find AssociatedBankAccountInfo by id.
     * @param id the AssociatedBankAccountInfo's id
     * @return AssociatedBankAccountInfo if found
     */
    @Override
    public Optional<AssociatedBankAccountInfo> findById(final Integer id) {
        return bankAccountInfoRepository.findById(id);
    }

    /**
     * Update information related to an existing AssociatedBankAccountInfo.
     * @param bankAccountInfo account to update
     * @return a call to the AssociatedBankAccountInfo repo layer
     */
    @Override
    public AssociatedBankAccountInfo updateBankAccountInfo(
            final AssociatedBankAccountInfo bankAccountInfo) {
        return bankAccountInfoRepository.save(bankAccountInfo);
    }
}
