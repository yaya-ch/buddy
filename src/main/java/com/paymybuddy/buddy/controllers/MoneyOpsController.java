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
     * @param iban the associated bank account's iban
     * @param amount the amount to be deposited
     * @param description a message in which the sender describes transactions
     * @return success message
     * @throws ElementNotFoundException if no email was found
     */
    @PutMapping("/deposit/{email}/{iban}/{amount}/{description}")
    public String depositMoneyOnAccount(@PathVariable final String email,
                                        @PathVariable final String iban,
                                        @PathVariable final Double amount,
                                        @PathVariable final String description)
            throws ElementNotFoundException, MoneyOpsException {
        String secureEmail = email.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        String secureIban = iban.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        String secureDescription = description.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        LOGGER.debug("Request to deposit {} on account {} from {}",
                amount, secureEmail, secureIban);
        moneyOpsService.depositMoneyOnBuddyAccount(
                secureEmail, secureIban, amount, secureDescription);
        return "Your account has been credited successfully";
    }

    /**
     * This method allows users to transfer money to their bank accounts.
     * @param email the user's email
     * @param iban the user's bank account iban(the associated bank account)
     * @param amount amount of money a user wants to transfer
     * @param description a message in which the sender describes transactions
     * @return a success message
     */
    @PutMapping("/transferToBank/{email}/{iban}/{amount}/{description}")
    public String transferMoneyToBankAccount(
            @PathVariable final String email,
            @PathVariable final String iban,
            @PathVariable final Double amount,
            @PathVariable final String description)
            throws ElementNotFoundException, MoneyOpsException {
        String secureEmail = email
                .replaceAll(DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        String secureIban = iban
                .replaceAll(DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        String secureDescription = description.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        LOGGER.debug("Transferring {} from {} to bank account {}",
                amount, secureEmail, secureIban);
        moneyOpsService.transferMoneyToBankAccount(
                secureEmail, secureIban, amount, secureDescription);
        return "Money transferred successfully to your bank account";
    }

    /**
     * This method allows users to transfer money to other users.
     * @param senderEmail the email of the user who wants to send money
     * @param receiverEmail the email of the user who will receive money
     * @param amount the amount to be sent
     * @param description a message in which the sender describes transactions
     * @return success message
     */
   @PutMapping("/transfer/{senderEmail}/{receiverEmail}/{amount}/{description}")
    public String transferMoneyToUsers(@PathVariable final String senderEmail,
                                       @PathVariable final String receiverEmail,
                                       @PathVariable final Double amount,
                                       @PathVariable final String description)
            throws MoneyOpsException, ElementNotFoundException {
        String secureSenderEmail = senderEmail.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        String secureReceiverEmail = receiverEmail
                .replaceAll(DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        String secureDescription = description.replaceAll(DANGEROUS_CHARACTERS,
                REPLACEMENT_CHARACTER);
        LOGGER.debug("Transferring {} from {} to {}",
                amount, secureSenderEmail, secureReceiverEmail);
        moneyOpsService.sendMoneyToUsers(
                secureSenderEmail, secureReceiverEmail,
                amount, secureDescription);
        return "Money transferred successfully";
    }
}
