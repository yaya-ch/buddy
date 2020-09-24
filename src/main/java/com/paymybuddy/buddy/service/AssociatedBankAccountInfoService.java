package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This interface contains abstract methods that provide
 * the logic to operated on the data sent to and from
 * the controllers and the repository layer
 */

public interface AssociatedBankAccountInfoService {

    /**
     * Find AssociatedBankAccountInfo by id.
     * @param id the AssociatedBankAccountInfo's id
     * @return AssociatedBankAccountInfo if found
     */
    Optional<AssociatedBankAccountInfo> findById(Integer id);

    /**
     * Update information related to an existing AssociatedBankAccountInfo.
     * @param bankAccountInfo account to update
     * @return  a call to the AssociatedBankAccountInfo repo layer
     */
    AssociatedBankAccountInfo updateBankAccountInfo(
            AssociatedBankAccountInfo bankAccountInfo);
}
