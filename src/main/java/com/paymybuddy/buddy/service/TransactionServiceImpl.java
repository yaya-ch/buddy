package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.TransactionConverter;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This class implements the TransactionService interface
 * @see TransactionService
 */

@Service
public class TransactionServiceImpl implements TransactionService {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(TransactionServiceImpl.class);
    /**
     * TransactionRepository to inject.
     */
    private final TransactionRepository transactionRepository;

    /**
     * UseRepository to inject.
     */
    private final UserRepository userRepository;

    /**
     * TransactionConverter to inject.
     */
    private final TransactionConverter transactionConverter;

    /**
     * Construction injection.
     * @param repository TransactionRepository
     * @param uRepository UserRepository
     * @param converter TransactionConverter
     */
    @Autowired
    public TransactionServiceImpl(final TransactionRepository repository,
                                  final UserRepository uRepository,
                                  final TransactionConverter converter) {
        this.transactionRepository = repository;
        this.userRepository = uRepository;
        this.transactionConverter = converter;
    }

    /**
     * @param id the transaction id
     * @return the found transaction
     * @throws ElementNotFoundException if no transaction was found
     */
    @Override
    public TransactionDTO findById(final Integer id)
            throws ElementNotFoundException {
         Optional<Transaction> checkForExistingTransaction =
                 transactionRepository.findById(id);
         if (checkForExistingTransaction.isPresent()) {
             LOGGER.info("Transaction loaded successfully {}", id);
             return transactionConverter
                     .transactionEntityToTransactionDTO(
                             checkForExistingTransaction.get());
         } else {
             LOGGER.error("Failed to load transaction {}."
                     + " No matching id was found", id);
             throw new ElementNotFoundException("Failed to load Transaction."
                     + " No matching transaction id was found");
         }
    }

    /**
     * @param id the user's id
     * @return a list of transactions (DTO)
     * @throws ElementNotFoundException if no matching user was found
     */
    @Override
    public List<TransactionDTO> findUserTransactions(final Integer id)
            throws ElementNotFoundException {
        List<Transaction> transactions =
                transactionRepository.findUserTransactions(id);
        Optional<User> checkForExistingUser = userRepository.findById(id);
        if (checkForExistingUser.isPresent()) {
            LOGGER.info("Transactions related to user {}"
                    + " loaded successfully", id);
            return transactionConverter
                    .transactionEntityListToTransactionDTOList(transactions);
        } else {
            LOGGER.error("Failed to load user transactions."
                    + " No user with id {} was found", id);
            throw new ElementNotFoundException("Failed to load transactions."
                    + " No matching user was found.");
        }
    }
}
