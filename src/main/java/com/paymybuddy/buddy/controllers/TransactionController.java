package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.service.TransactionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * This controller exposes some Transaction related endpoints
 */

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(TransactionController.class);

    /**
     * TransactionService to be injected.
     */
    private final TransactionService transactionService;

    /**
     * Constructor injection.
     * @param service TransactionService
     */
    @Autowired
    public TransactionController(final TransactionService service) {
        this.transactionService = service;
    }

    /**
     * Load transaction by id.
     * @param id the transaction id
     * @return the found transaction (DTO)
     * @throws ElementNotFoundException if no transaction was found
     */
    @GetMapping("/findById/{id}")
    public TransactionDTO findById(@PathVariable final Integer id)
            throws ElementNotFoundException {
        LOGGER.debug("Trying to load transaction {}", id);
        return transactionService.findById(id);
    }

    /**
     * Retrieve the user's transactions.
     * @param id the user's id
     * @return a call to the service layer(which returns a list of transactions)
     * @throws ElementNotFoundException if no matching user was found
     */
    @GetMapping("/findTransactions/{id}")
    public List<TransactionDTO> findUserTransactions(
            @PathVariable final Integer id)
            throws ElementNotFoundException {
        LOGGER.debug("Loading transactions related to user {}", id);
        return transactionService.findUserTransactions(id);
    }
}
