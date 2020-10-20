package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.constants.ConstantNumbers;
import org.springframework.stereotype.Service;

/**
 * @author Yahia CHERIFI
 * This class implements MonetizingService
 * @see MonetizingService
 */

@Service
public class MonetizingServiceImpl implements MonetizingService {

    /**
     * @param amount amount of money transferred by a user.
     * @return 0.5% of the amount transferred by a user
     */
    @Override
    public Double transactionFee(final Double amount) {
        return (amount * ConstantNumbers.FEE_PERCENTAGE);
    }
}
