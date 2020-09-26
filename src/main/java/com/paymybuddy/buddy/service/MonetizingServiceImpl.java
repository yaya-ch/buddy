package com.paymybuddy.buddy.service;

import org.springframework.stereotype.Service;

/**
 * @author Yahia CHERIFI
 * This class implements MonetizingService
 * @see MonetizingService
 */

@Service
public class MonetizingServiceImpl implements MonetizingService {

    /**
     * Fee that will be subtracted from.
     * the amount that a user wants to transfer
     */
    private static final double FEE_PERCENTAGE = 0.005;
    /**
     * @param amount amount of money transferred by a user.
     * @return 0.5% of the amount transferred by a user
     */
    @Override
    public Double transactionFee(final Double amount) {
        return (amount * FEE_PERCENTAGE);
    }
}
