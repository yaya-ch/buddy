package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.constants.ConstantNumbers;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Yahia CHERIFI
 * This class implements MoneyOpsService interface
 * @see MoneyOpsService
 */

@Service
public class MoneyOpsServiceImpl implements MoneyOpsService {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(MoneyOpsServiceImpl.class);

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
     * TransactionRepository to inject.
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
     *
     * @param email the user's email
     * @param iban the associated bank account's iban
     * @param amount amount of money to deposit
     * @throws ElementNotFoundException if no matching email was found in db
     * @throws MoneyOpsException if errors occur while transferring money
     */
    @Transactional
    @Override
    public void depositMoneyOnBuddyAccount(final String email,
                                           final String iban,
                                           final Double amount)
            throws ElementNotFoundException, MoneyOpsException {
        User checkForExistingUser =
                userRepository.findByEmail(email);

        if (checkForExistingUser != null) {
            //CHECK IF THE PROVIDED IBAN MATCHES THE ASSOCIATED ONE
            String userIban = checkForExistingUser
                    .getBuddyAccountInfo().getAssociatedBankAccountInfo()
                    .getIban();
            if (!userIban.equals(iban)) {
                LOGGER.error("Failed to transfer {} to {} from {}."
                        + " Bank account is different from the associated one",
                        amount, email, iban);
                //CREATE A NEW REJECTED TRANSACTION
                transactionsBetweenBuddyAccountAndBankAccount(
                        checkForExistingUser, amount, null,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                throw new MoneyOpsException("Failed"
                        + " to transfer money from the provided bank account."
                        + " You can only transfer money from"
                        + " the associated bank account");
            }
            //CONTROL THE AMOUNT OF MONEY THAT A USER CAN DEPOSIT
            if (amount > ConstantNumbers.MAX_MONEY_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to credit account {}."
                        + " Amount must be greater than 0"
                        + " and less than or equals to 1000",
                        email);
                //CREATE A NEW REJECTED TRANSACTION
                transactionsBetweenBuddyAccountAndBankAccount(
                        checkForExistingUser, amount, null,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                LOGGER.error("Failed to deposit money on {}."
                        + " The provided amount '{}' does not meet"
                        + " the requirements", email, amount);
                throw new MoneyOpsException(
                        "Failed to deposit money on account."
                        + " You cannot deposit 0 buddies"
                        + " or more than 1000 buddies");
            }
            //GET THE USER'S ID
            Integer id = checkForExistingUser.getUserId();
            //GET THE USER'S ACTUAL ACCOUNT BALANCE
            Double getActualAccountBalance = checkForExistingUser
                    .getBuddyAccountInfo().getActualAccountBalance();
            //CALCULATE FEE
            Double fee = monetizingService.transactionFee(amount);
            //CALCULATE THE NEW ACTUAL ACCOUNT BALANCE
            Double updatedActualAccountBalance =
                    getActualAccountBalance + (amount - fee);
            //CREATE A NEW ACCEPTED TRANSACTION
            transactionsBetweenBuddyAccountAndBankAccount(
                    checkForExistingUser, amount, fee,
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            //UPDATE THE ACTUAL BUDDY ACCOUNT BALANCE
            buddyAccountInfoRepository.updateActualAccountBalance(
                    id, updatedActualAccountBalance);
            //UPDATE THE PREVIOUS BUDDY ACCOUNT BALANCE
            buddyAccountInfoRepository.updatePreviousAccountBalance(
                    id, getActualAccountBalance);
            LOGGER.info("Account balance updated successfully."
                    + " {} deposited on {}", amount, email);
        } else {
            LOGGER.error("Failed to deposit {} on account {}."
                    + " This account does not exist", amount, email);
            throw new ElementNotFoundException(
                    "Failed to deposit money on the account."
                    + " Account does not exist");
        }
    }

    /**
     *
     * @param email the user's email
     * @param iban the user's bank account iban
     * @param amount the amount that users want
     *               to transfer to their bank accounts
     * @throws ElementNotFoundException if no matching email was found in db
     * @throws MoneyOpsException if errors occur while transferring money
     */
    @Transactional
    @Override
    public void transferMoneyToBankAccount(
            final String email,
            final String iban,
            final Double amount)
            throws ElementNotFoundException, MoneyOpsException {

        //******CHECK IF THE PROVIDED EMAIL EXISTS IN DB************
        User checkForExistingUser = userRepository.findByEmail(email);
        if (checkForExistingUser != null) {
            //******GET THE USER'S ID************
            Integer userId = checkForExistingUser.getUserId();
            //******GET THE USER'S IBAN************
            String userIban = checkForExistingUser
                    .getBuddyAccountInfo()
                    .getAssociatedBankAccountInfo().getIban();
            //******CHECK IF THE USER'S IBAN IS CORRECT************
            if (!iban.equals(userIban)) {
                LOGGER.error("Transferring canceled. "
                            + "{} tried to transfer money to an other"
                            + " bank account {}", email, iban);
                //******CREATE A NEW REJECTED TRANSACTION************
                transactionsBetweenBuddyAccountAndBankAccount(
                        checkForExistingUser, amount, null,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                throw new MoneyOpsException(
                        "The provided iban is different from the one"
                                + " associated to your account."
                                + " Failed to transfer money"
                                + " to other bank account.");
            }

            //******CALCULATE FEE************
            Double fee = monetizingService.transactionFee(amount);
            //******GET THE USER ACCOUNT BALANCE************
            Double currentAccountBalance = checkForExistingUser
                    .getBuddyAccountInfo().getActualAccountBalance();
            //******CHECK IF THE USER HAS ENOUGH MONEY************
            if ((fee + amount) > currentAccountBalance) {
                LOGGER.error("Failed to transfer money from {} to bank account."
                        + " {}. Insufficient balance", email, iban);
                //******CREATE A NEW REJECTED TRANSACTION************
                transactionsBetweenBuddyAccountAndBankAccount(
                        checkForExistingUser, amount, null,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                throw new MoneyOpsException(
                        "Failed to transfer money to your bank account."
                                + " You do not have enough money.");
            }
            Double updateUserAccountBalance =
                    currentAccountBalance - (amount - fee);
            //******CREATE A NEW ACCEPTED TRANSACTION************
            transactionsBetweenBuddyAccountAndBankAccount(
                    checkForExistingUser, amount, fee,
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);

            //******UPDATE THE USER'S ACCOUNT BALANCE**********
            LOGGER.info("Money transferred successfully from"
                    + " {} to bank account {}", email, iban);
            buddyAccountInfoRepository
                    .updateActualAccountBalance(
                            userId, updateUserAccountBalance);
            buddyAccountInfoRepository
                    .updatePreviousAccountBalance(
                            userId, currentAccountBalance);

        } else {
            LOGGER.error("Failed to transfer money."
                    + " There is no matching email found");
            throw new ElementNotFoundException(
                    "There is no matching email addresses."
                            + " Please check your input.");
        }
    }

    /**
     *
     * @param senderEmail the email of the sender
     * @param recipientEmail the email address of the recipient
     * @param amount the amount that will be sent
     * @throws MoneyOpsException if errors occur while transferring money
     * @throws ElementNotFoundException if no matching email was found in db
     */
    @Transactional
    @Override
    public void sendMoneyToUsers(final String senderEmail,
                                 final String recipientEmail,
                                 final Double amount) throws MoneyOpsException,
                                                ElementNotFoundException {
        //CHECK IF THE SENDER'S EMAIL EXISTS IN DB
        User checkForSender = userRepository.findByEmail(senderEmail);
        //CHECK IF THE RECIPIENT'S EMAIL EXISTS IN DB
        User checkForRecipient = userRepository.findByEmail(recipientEmail);
        if (checkForSender != null && checkForRecipient != null) {
            //CONTROL THE AMOUNT OF MONEY THAT A USER CAN TRANSFER
            if (amount > ConstantNumbers.MAX_MONEY_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to transfer {} buddies to {}"
                                + " Amount must be greater than 0"
                                + " and less than or equals to 1000",
                                  amount, recipientEmail);
                //CREATE A NEW REJECTED TRANSACTION
                transactionsBetweenContacts(
                        checkForSender, checkForRecipient, amount,
                        null, TransactionStatusInfo.SENDING_IN_PROGRESS,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                throw new MoneyOpsException(
                        "Failed to deposit money on account."
                        + " You cannot deposit 0 buddies"
                        + " or more than 1000 buddies");
            }
            //THE LOGIC THAT WILL BE USED FOR THE OPERATIONS
            Integer senderId = checkForSender.getUserId();
            Integer recipientId = checkForRecipient.getUserId();
            //CHECK THE SENDER'S ACCOUNT BALANCE
            Double senderActualAccountBalance =
                    checkForSender.getBuddyAccountInfo()
                    .getActualAccountBalance();
            //GET THE RECIPIENT'S ACTUAL ACCOUNT BALANCE
            Double recipientActualAccountBalance =
                    checkForRecipient.getBuddyAccountInfo()
                    .getActualAccountBalance();
            Double fee = monetizingService.transactionFee(amount);
            if ((amount + fee) > senderActualAccountBalance) {
                LOGGER.error("Transfer canceled."
                        + " You do not have enough money on your account");
                //CREATE A NEW REJECTED TRANSACTION
                transactionsBetweenContacts(checkForSender,
                        checkForRecipient, amount, null,
                        TransactionStatusInfo.SENDING_IN_PROGRESS,
                        TransactionStatusInfo.TRANSACTION_REJECTED);
                throw new MoneyOpsException(
                        "You cannot transfer money. Insufficient balance");
            }

            Double updatedSenderAccountBalance =
                    senderActualAccountBalance - amount - fee;
            //CREATE A NEW ACCEPTED TRANSACTION FOR THE SENDER
            transactionsBetweenContacts(
                    checkForSender, checkForRecipient, amount, fee,
                    TransactionStatusInfo.SENDING_IN_PROGRESS,
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            //UPDATE THE SENDER'S ACTUAL ACCOUNT BALANCE
            buddyAccountInfoRepository.updateActualAccountBalance(
                    senderId, updatedSenderAccountBalance);
            //UPDATE THE SENDER'S PREVIOUS ACCOUNT BALANCE
            buddyAccountInfoRepository
                    .updatePreviousAccountBalance(
                            senderId, senderActualAccountBalance);
            LOGGER.info("{} sent from {} to {} successfully",
                    amount, senderEmail, recipientEmail);
            //*******RECIPIENT******//
            Double updatedRecipientAccountBalance =
                    recipientActualAccountBalance + amount;
            //CREATE A NEW ACCEPTED TRANSACTION FOR RECIPIENT
            transactionsBetweenContacts(
                    checkForSender, checkForRecipient, amount, null,
                    TransactionStatusInfo.UP_COMING_TRANSACTION,
                    TransactionStatusInfo.MONEY_RECEIVED);
            //UPDATE THE RECIPIENT'S ACTUAL ACCOUNT BALANCE
            buddyAccountInfoRepository
                    .updateActualAccountBalance(
                            recipientId, updatedRecipientAccountBalance);
            //UPDATE THE RECIPIENT'S PREVIOUS ACCOUNT BALANCE
            buddyAccountInfoRepository
                    .updatePreviousAccountBalance(
                            recipientId, recipientActualAccountBalance);
            LOGGER.info("{} received from {}", amount, senderEmail);

        } else {
            LOGGER.error("Operation canceled. "
                    + " Emails provided are incorrect {} and {}",
                    senderEmail, recipientEmail);
            throw new ElementNotFoundException(
                    "There is no matching email addresses."
                    + " Please check your input.");
        }
    }

    /**
     * This local method is used to create transactions.
     *                  between buddy and bank accounts
     * @param user the user to whom the
     *             transaction will be added
     * @param amount amount of money that will be sent
     * @param fee the fee that will be paid by the user who sends money
     * @param finalTransactionStatusInfo final transaction status
     */
    private void transactionsBetweenBuddyAccountAndBankAccount(
            final User user, final Double amount, final Double fee,
            final TransactionStatusInfo finalTransactionStatusInfo) {
        Transaction transaction = new Transaction();
        BuddyAccountInfo senderRecipient = user.getBuddyAccountInfo();
        transaction.setSender(senderRecipient);
        transaction.setRecipient(senderRecipient);
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setTransactionNature(
                TransactionNature.BETWEEN_ACCOUNTS);
        //INITIAL STATUS
        transaction.setInitialTransactionStatusInfo(
                TransactionStatusInfo.SENDING_IN_PROGRESS);
        transaction.setInitialTransactionStatusInfoDate(new Date());
        //FINAL STATUS
        transaction.setFinalTransactionStatusInfo(finalTransactionStatusInfo);
        transaction.setFinalTransactionStatusInfoDate(new Date());
        //SAVE THE TRANSACTION
        transactionRepository.save(transaction);
    }

    /**
     * This local method is used to create transactions.
     *          between different users of PayMyBuddy
     * @param sender the user who will send money
     * @param recipient the user to whom money will be sent
     * @param amount amount of money sent/received
     * @param fee the fee that will be paid by the user
     * @param initialTransactionStatusInfo initial transaction status
     * @param finalTransactionStatusInfo final transaction status
     */
    private void transactionsBetweenContacts(
            final User sender,
            final User recipient,
            final Double amount,
            final Double fee,
            final TransactionStatusInfo initialTransactionStatusInfo,
            final TransactionStatusInfo finalTransactionStatusInfo) {
        Transaction transaction = new Transaction();
        transaction.setSender(sender.getBuddyAccountInfo());
        transaction.setRecipient(recipient.getBuddyAccountInfo());
        transaction.setAmount(amount);
        transaction.setFee(fee);
        transaction.setTransactionNature(
                TransactionNature.TO_CONTACTS);
        //INITIAL STATUS
        transaction.setInitialTransactionStatusInfo(
                initialTransactionStatusInfo);
        transaction.setInitialTransactionStatusInfoDate(new Date());
        //FINAL STATUS
        transaction.setFinalTransactionStatusInfo(finalTransactionStatusInfo);
        transaction.setFinalTransactionStatusInfoDate(new Date());
        //SAVE THE TRANSACTION
        transactionRepository.save(transaction);
    }
}
