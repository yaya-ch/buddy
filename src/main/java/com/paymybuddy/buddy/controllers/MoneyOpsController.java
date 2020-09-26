package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.service.MoneyOpsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YAhia CHERIFI
 * This controller permits to users to transfer money
 * to either their personal bank accounts or to one of their contacts
 */

@RestController
@RequestMapping("/operations")
public class MoneyOpsController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(MoneyOpsController.class);

    /**
     * Control user related input to avoid injection.
     * these characters will be omitted
     */
    private static final String DANGEROUS_CHARACTERS =  "[\n\r\t]";

    /**
     * Used to replace some dangerous.
     * characters that may be injected by the user
     */
    private static final String REPLACEMENT_CHARACTER = "_";

    /**
     * MoneyOpsService to inject.
     */
    private final MoneyOpsService moneyOpsService;

    /**
     * Constructor injection.
     * @param service MoneyOpsService
     */
    @Autowired
    public MoneyOpsController(final MoneyOpsService service) {
        this.moneyOpsService = service;
    }

    /**
     * This method allows to users to deposit money on their buddy accounts.
     * @param email the user's email address
     * @param amount the amount to be deposited
     * @return success message
     * @throws ElementNotFoundException if no email was found
     */
    @PutMapping("/deposit/{email}/{amount}")
    public String depositMoneyOnAccount(@PathVariable final String email,
                                        @PathVariable final Double amount)
            throws ElementNotFoundException, MoneyOpsException {
        String secureEmail = email.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        LOGGER.debug("Request to deposit {} on account {} ",
                secureEmail, amount);
        moneyOpsService.depositMoneyOnAccount(email, amount);
        return "Your account has been credited successfully";
    }

    /**
     * This method allows users to transfer money to other users.
     * @param senderEmail the email of the user who wants to send money
     * @param receiverEmail the email of the user who will receive money
     * @param amount the amount to be sent
     * @return success message
     */
    @PutMapping("/transfer/{senderEmail}/{receiverEmail}/{amount}")
    public String transferMoneyToUsers(@PathVariable final String senderEmail,
                                       @PathVariable final String receiverEmail,
                                       @PathVariable final Double amount)
            throws MoneyOpsException, ElementNotFoundException {
        LOGGER.debug("Transferring {} from {} to {}",
                amount, senderEmail, receiverEmail);
        moneyOpsService.sendMoneyToUsers(senderEmail, receiverEmail, amount);
        return "Money transferred successfully";
    }
}
