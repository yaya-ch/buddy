package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.converters.TransactionConverter;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.TransactionDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
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
     * BuddyAccountInfoRepository to inject.
     */
    private final BuddyAccountInfoRepository buddyAccountInfoRepository;

    /**
     * TransactionConverter to inject.
     */
    private final TransactionConverter transactionConverter;

    /**
     * Construction injection.
     * @param repository TransactionRepository
     * @param converter TransactionConverter
     * @param bRepository BuddyAccountInfoRepository
     */
    @Autowired
    public TransactionServiceImpl(final TransactionRepository repository,
                                final TransactionConverter converter,
                                final BuddyAccountInfoRepository bRepository) {
        this.transactionRepository = repository;
        this.transactionConverter = converter;
        this.buddyAccountInfoRepository = bRepository;
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
     * @param id the buddy account id
     * @return a list of transactions (DTO)
     * @throws ElementNotFoundException if no matching user was found
     */
    @Override
    public List<TransactionDTO> loadSenderTransactions(final Integer id)
            throws ElementNotFoundException {
        Optional<BuddyAccountInfo> checkForExistingBuddyAccount =
                buddyAccountInfoRepository.findById(id);
        if (checkForExistingBuddyAccount.isPresent()) {
            List<Transaction> transactions =
                    transactionRepository.findSenderTransactions(id);
            LOGGER.info("Sent transactions related to buddy account {} loaded"
                    + " successfully", id);
            return transactionConverter
                    .transactionEntityListToTransactionDTOList(transactions);
        } else {
            LOGGER.error("Failed to load user transactions."
                    + " No user with id {} was found", id);
            throw new ElementNotFoundException("Failed to load transactions."
                    + " No matching user was found.");
        }
    }

    /**
     * @param id buddy account id
     * @return a list of transactions
     * @throws ElementNotFoundException if no matching buddy account found
     */
    @Override
    public List<TransactionDTO> loadRecipientTransactions(final Integer id)
            throws ElementNotFoundException {
        Optional<BuddyAccountInfo> buddyAccountInfo =
                buddyAccountInfoRepository.findById(id);
        if (buddyAccountInfo.isPresent()) {
            List<Transaction> recipientTransactions =
                    transactionRepository.findRecipientTransactions(id);
            LOGGER.info("Received transactions related buddy account {}"
                    + " loaded successfully", id);
            return transactionConverter
                    .transactionEntityListToTransactionDTOList(
                    recipientTransactions);
        } else {
            LOGGER.error("Failed to load received transactions related to buddy"
                    + " account {}. No matching account found", id);
            throw new ElementNotFoundException("Failed to load received"
                    + " transactions related to buddy account " + id);
        }
    }
}
