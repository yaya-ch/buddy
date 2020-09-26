package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.exceptions.MoneyOpsException;
import com.paymybuddy.buddy.repository.BuddyAccountInfoRepository;
import com.paymybuddy.buddy.repository.TransactionRepository;
import com.paymybuddy.buddy.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Yahia CHERIFI
 * This class implements MoneyOpsService interface
 * @see MoneyOpsService
 */

@Service
public class MoneyOpsServiceImpl implements MoneyOpsService {

    /**
     * Represents the max amount that can be deposited by a user.
     */
    private static final int MAX_ALLOWED = 1000;

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(MoneyOpsServiceImpl.class);

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
     * User repository to inject.
     */
    private final UserRepository userRepository;

    /**
     * BuddyAccountInfoRepository to inject.
     */
    private final BuddyAccountInfoRepository buddyAccountInfoRepository;

    /**
     * MonetizingService to inject.
     */
    private final MonetizingService monetizingService;

    /**
     *
     */
    private final TransactionRepository transactionRepository;

    /**
     * Constructor injection.
     * @param uRepository UserRepository
     * @param bRepository BuddyAccountInfoRepository
     * @param mService    MonetizingService
     * @param tRepository TransactionRepository
     */
    @Autowired
    public MoneyOpsServiceImpl(final UserRepository uRepository,
                               final BuddyAccountInfoRepository bRepository,
                               final MonetizingService mService,
                               final TransactionRepository tRepository) {
        this.userRepository = uRepository;
        this.buddyAccountInfoRepository = bRepository;
        this.monetizingService = mService;
        this.transactionRepository = tRepository;
    }

    /**
     * @param email  the user's email
     * @param amount amount of money to deposit
     */
    @Override
    public void depositMoneyOnAccount(final String email, final Double amount)
            throws ElementNotFoundException, MoneyOpsException {
        String secureEmail = email.replaceAll(
                DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        Transaction transaction;
        User checkForExistingUser =
                userRepository.findByEmail(email);

        if (checkForExistingUser != null) {
            //control the amount of money that a user can deposit
            if (amount > MAX_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to credit account {}."
                        + " Amount must be greater than 0"
                        + " and less than or equals to 1000",
                        secureEmail);
                transaction = new Transaction();
                transaction.setTransactionDate(new Date());
                transaction.setAmount(amount);
                transaction.setTransactionNature(
                        TransactionNature.BETWEEN_ACCOUNTS);
                transaction.setTransactionStatusInfo(
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                checkForExistingUser.getBuddyAccountInfo()
                        .addNewTransaction(transaction);
                transactionRepository.save(transaction);
                LOGGER.error("Failed to deposit money on {}."
                        + " The provided amount '{}' does not meet"
                        + " the requirements", secureEmail, amount);
                throw new MoneyOpsException(
                        "Failed to deposit money on account."
                        + " You cannot deposit 0 buddies"
                        + " or more than 1000 buddies");
            }
            //get the user's id
            Integer id = checkForExistingUser.getUserId();
            //get the user's account balance
            Double accountBalance = checkForExistingUser
                    .getBuddyAccountInfo().getAccountBalance();
            //calculate fee
            Double fee = monetizingService.transactionFee(amount);
            Double updatedAccountBalance = accountBalance + (amount - fee);
            //create new transaction
            transaction = new Transaction();
            transaction.setTransactionDate(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionNature(
                    TransactionNature.BETWEEN_ACCOUNTS);
            transaction.setTransactionStatusInfo(
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            //add the transaction to user's buddy account info
            checkForExistingUser.getBuddyAccountInfo()
                    .addNewTransaction(transaction);
            //update the wright account
            buddyAccountInfoRepository.updateBalance(id, updatedAccountBalance);
            LOGGER.info("Account balance updated successfully."
                    + " {} deposited on {}", amount, secureEmail);
        } else {
            LOGGER.error("Failed to deposit {} on account {}."
                    + " This account does not exist", amount, secureEmail);
            throw new ElementNotFoundException(
                    "Failed to deposit money on the account."
                    + " Account does not exist");
        }
    }

    /**
     * @param senderEmail   the email of the sender
     * @param receiverEmail the email of the user who will receive money
     * @param amount        the amount that will be sent
     */
    @Override
    public void sendMoneyToUsers(final String senderEmail,
                                 final String receiverEmail,
                                 final Double amount) throws MoneyOpsException,
                                                ElementNotFoundException {
        String secureSenderEmail = senderEmail.replaceAll(
                DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        String secureReceiverEmail = receiverEmail.replaceAll(
                DANGEROUS_CHARACTERS, REPLACEMENT_CHARACTER);
        //check if the sender's email is correct
        User checkForSender = userRepository.findByEmail(senderEmail);
        //check if the receiver's email exist in db
        User checkForReceiver = userRepository.findByEmail(receiverEmail);
        if (checkForSender != null && checkForReceiver != null) {
            //control the amount of money that a user can send
            if (amount > MAX_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to transfer {} buddies to {}"
                                + " Amount must be greater than 0"
                                + " and less than or equals to 1000",
                                  amount, secureReceiverEmail);
                rejectedTransaction(amount, checkForSender);
                throw new MoneyOpsException(
                        "Failed to deposit money on account."
                        + " You cannot deposit 0 buddies"
                        + " or more than 1000 buddies");
            }
            //The logic that will be used for operations
            Integer senderId = checkForSender.getUserId();
            Integer receiverId = checkForReceiver.getUserId();
            //check the sender's account balance
            Double senderAccountBalance =
                    checkForSender.getBuddyAccountInfo().getAccountBalance();
            //Get the receiver's account balance
            Double receiverAccountBalance =
                    checkForReceiver.getBuddyAccountInfo().getAccountBalance();
            Double fee = monetizingService.transactionFee(amount);
            if ((amount + fee) > senderAccountBalance) {
                LOGGER.error("Transfer canceled."
                        + " You do not have enough money on your account");
                rejectedTransaction(amount, checkForSender);
                throw new MoneyOpsException(
                        "You cannot transfer money. Insufficient balance");
            }

            Double updatedSenderAccountBalance =
                    senderAccountBalance - amount - fee;
            //create new transaction
            Transaction transaction = new Transaction();
            transaction.setTransactionDate(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionNature(
                    TransactionNature.TO_CONTACTS);
            transaction.setTransactionStatusInfo(
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            //add the transaction to user's buddy account info
            checkForSender.getBuddyAccountInfo()
                    .addNewTransaction(transaction);
            //update the sender's's account balance
            buddyAccountInfoRepository.updateBalance(
                    senderId, updatedSenderAccountBalance);
            //*******receiver******//
            Double updatedReceiverAccountBalance =
                    receiverAccountBalance + amount;
            transaction = new Transaction();
            transaction.setTransactionDate(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionNature(
                    TransactionNature.TO_CONTACTS);
            transaction.setTransactionStatusInfo(
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            //add the transaction to user's buddy account info
            checkForReceiver.getBuddyAccountInfo()
                    .addNewTransaction(transaction);
            //update the receiver's account
            buddyAccountInfoRepository
                    .updateBalance(receiverId, updatedReceiverAccountBalance);

        } else {
            LOGGER.error("Operation canceled. "
                    + " Emails provided are incorrect {} and {}",
                    secureSenderEmail, secureReceiverEmail);
            throw new ElementNotFoundException(
                    "There is no matching email addresses."
                    + " Please check your input.");
        }

    }

    /**
     * Set a rejected transaction.
     * @param amount amount of money
     * @param checkForSender check if a user who sends money exists in db
     */
    private void rejectedTransaction(final Double amount,
                                     final User checkForSender) {
        Transaction transaction = new Transaction();
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setTransactionNature(
                TransactionNature.TO_CONTACTS);
        transaction.setTransactionStatusInfo(
                TransactionStatusInfo.TRANSACTION_REJECTED);
        checkForSender.getBuddyAccountInfo()
                .addNewTransaction(transaction);
        transactionRepository.save(transaction);
    }
}
