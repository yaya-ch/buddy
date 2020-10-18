package com.paymybuddy.buddy.service;

import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.domain.User;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionProperty;
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
     * Represents the max amount that can be deposited by a user.
     */
    private static final int MAX_ALLOWED = 1000;

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
     * @param iban the associated bank account's iban
     * @param amount amount of money to deposit
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
                rejectTransactionFromBankAccountToBuddyAccount(
                        amount, iban, checkForExistingUser);
                throw new MoneyOpsException("Failed"
                        + " to transfer money from the provided bank account."
                        + " You can only transfer money from"
                        + " the associated bank account");
            }
            //CONTROL THE AMOUNT OF MONEY THAT A USER CAN DEPOSIT
            if (amount > MAX_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to credit account {}."
                        + " Amount must be greater than 0"
                        + " and less than or equals to 1000",
                        email);
                //CREATE A NEW REJECTED TRANSACTION
                rejectTransactionFromBankAccountToBuddyAccount(
                        amount, iban, checkForExistingUser);
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
            //GET THE USER'S ACCOUNT BALANCE
            Double accountBalance = checkForExistingUser
                    .getBuddyAccountInfo().getActualAccountBalance();
            //CALCULATE FEE(
            Double fee = monetizingService.transactionFee(amount);
            Double updatedAccountBalance = accountBalance + (amount - fee);
            //CREATE A NEW ACCEPTED TRANSACTION
            Transaction transaction = new Transaction();
            transaction.setSender(iban);
            transaction.setRecipient(checkForExistingUser.getEmail());
            transaction.setTransactionDate(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionNature(
                    TransactionNature.BETWEEN_ACCOUNTS);
            transaction.setTransactionStatusInfo(
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            transaction.setTransactionProperty(TransactionProperty.RECEIVED);
            //ADD THE TRANSACTION TO THE USER'S BUDDY ACCOUNT INFO
            checkForExistingUser.getBuddyAccountInfo()
                    .addNewTransaction(transaction);
            //UPDATE THE BUDDY ACCOUNT BALANCE
            buddyAccountInfoRepository.updateBalance(id, updatedAccountBalance);
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
     * @param email  the user's email
     * @param iban   the user's bank account iban
     * @param amount the amount that users want
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
                rejectTransactionFromBuddyAccountToBankAccount(
                        amount, iban, checkForExistingUser);
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
                        + " {}", email, iban);
                //******CREATE A NEW REJECTED TRANSACTION************
                rejectTransactionFromBuddyAccountToBankAccount(
                        amount, iban, checkForExistingUser);
                throw new MoneyOpsException(
                        "Failed to transfer money to your bank account."
                                + " You do not have enough money.");
            }

            Double updateUserAccountBalance =
                    currentAccountBalance - (amount - fee);
            //******CREATE A NEW ACCEPTED TRANSACTION************
            Transaction transaction = new Transaction();
            transaction.setSender(email);
            transaction.setRecipient(iban);
            transaction.setTransactionDate(new Date());
            transaction.setAmount(amount);
            transaction.setTransactionNature(
                    TransactionNature.BETWEEN_ACCOUNTS);
            transaction.setTransactionStatusInfo(
                    TransactionStatusInfo.TRANSACTION_ACCEPTED);
            transaction.setTransactionProperty(TransactionProperty.SENT);
            checkForExistingUser.getBuddyAccountInfo()
                    .addNewTransaction(transaction);

            //******UPDATE THE USER'S ACCOUNT BALANCE**********
            LOGGER.info("Money transferred successfully from"
                    + " {} to bank account {}", email, iban);
            buddyAccountInfoRepository
                    .updateBalance(userId, updateUserAccountBalance);

        } else {
            LOGGER.error("Failed to transfer money."
                    + " There is no matching email found");
            throw new ElementNotFoundException(
                    "There is no matching email addresses."
                            + " Please check your input.");
        }
    }

    /**
     * @param senderEmail   the email of the sender
     * @param receiverEmail the email of the user who will receive money
     * @param amount        the amount that will be sent
     */
    @Transactional
    @Override
    public void sendMoneyToUsers(final String senderEmail,
                                 final String receiverEmail,
                                 final Double amount) throws MoneyOpsException,
                                                ElementNotFoundException {
        User checkForSender = userRepository.findByEmail(senderEmail);
        //CHECK IF THE RECEIVER'S EMAIL EXISTS IN DB
        User checkForReceiver = userRepository.findByEmail(receiverEmail);
        if (checkForSender != null && checkForReceiver != null) {
            //CONTROL THE AMOUNT OF MONEY THAT A USER CAN TRANSFER
            if (amount > MAX_ALLOWED || amount <= 0) {
                LOGGER.error("Failed to transfer {} buddies to {}"
                                + " Amount must be greater than 0"
                                + " and less than or equals to 1000",
                                  amount, receiverEmail);
                rejectedTransactionBetweenContacts(
                        amount, receiverEmail, checkForSender);
                throw new MoneyOpsException(
                        "Failed to deposit money on account."
                        + " You cannot deposit 0 buddies"
                        + " or more than 1000 buddies");
            }
            //THE LOGIC THAT WILL BE USED FOR THE OPERATIONS
            Integer senderId = checkForSender.getUserId();
            Integer receiverId = checkForReceiver.getUserId();
            //CHECK THE SENDER'S ACCOUNT BALANCE
            Double senderAccountBalance =
                    checkForSender.getBuddyAccountInfo()
                    .getActualAccountBalance();
            //GET THE RECEIVER'S ACCOUNT BALANCE
            Double receiverAccountBalance =
                    checkForReceiver.getBuddyAccountInfo()
                    .getActualAccountBalance();
            Double fee = monetizingService.transactionFee(amount);
            if ((amount + fee) > senderAccountBalance) {
                LOGGER.error("Transfer canceled."
                        + " You do not have enough money on your account");
                rejectedTransactionBetweenContacts(
                        amount, receiverEmail, checkForSender);
                throw new MoneyOpsException(
                        "You cannot transfer money. Insufficient balance");
            }

            Double updatedSenderAccountBalance =
                    senderAccountBalance - amount - fee;
            //CREATE A NEW ACCEPTED TRANSACTION FOR THE SENDER
            acceptedTransactionBetweenContacts(
                    senderEmail, receiverEmail, amount,
                    checkForSender, TransactionProperty.SENT);
            //UPDATE THE SENDER'S ACCOUNT BALANCE
            buddyAccountInfoRepository.updateBalance(
                    senderId, updatedSenderAccountBalance);
            LOGGER.info("{} sent from {} to {} successfully",
                    amount, senderEmail, receiverEmail);
            //*******RECEIVER******//
            Double updatedReceiverAccountBalance =
                    receiverAccountBalance + amount;
            //CREATE A NEW ACCEPTED TRANSACTION FOR RECEIVER
            acceptedTransactionBetweenContacts(
                    senderEmail, receiverEmail, amount,
                    checkForReceiver, TransactionProperty.RECEIVED);
            //UPDATE THE RECEIVER'S ACCOUNT BALANCE
            buddyAccountInfoRepository
                    .updateBalance(receiverId, updatedReceiverAccountBalance);
            LOGGER.info("{} received from {}", amount, senderEmail);

        } else {
            LOGGER.error("Operation canceled. "
                    + " Emails provided are incorrect {} and {}",
                    senderEmail, receiverEmail);
            throw new ElementNotFoundException(
                    "There is no matching email addresses."
                    + " Please check your input.");
        }
    }

    /**
     * Set a rejected transaction for operations.
     * that come from bank to buddy account
     * @param amount amount of money
     * @param sender the user who sent money
     * @param user the user to whom the
     *                             transaction will be added
     */
    private void rejectTransactionFromBankAccountToBuddyAccount(
            final Double amount, final String sender, final User user) {
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setRecipient(user.getEmail());
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setTransactionNature(
                TransactionNature.BETWEEN_ACCOUNTS);
        transaction.setTransactionStatusInfo(
                TransactionStatusInfo.TRANSACTION_REJECTED);
        transaction.setTransactionProperty(
                TransactionProperty.DEPOSITING_FAILED);
        user.getBuddyAccountInfo()
                .addNewTransaction(transaction);
        transactionRepository.save(transaction);
    }

    /**
     * Set a rejected transaction for operations.
     * that come from buddy to bank account
     * @param amount amount of money
     * @param iban the associated bank account's iban
     *             to which the money will be transferred
     * @param user the user to whom the
     *                             transaction will be added
     */
    private void rejectTransactionFromBuddyAccountToBankAccount(
            final Double amount, final String iban, final User user) {
        Transaction transaction = new Transaction();
        transaction.setSender(user.getEmail());
        transaction.setRecipient(iban);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setTransactionNature(
                TransactionNature.BETWEEN_ACCOUNTS);
        transaction.setTransactionStatusInfo(
                TransactionStatusInfo.TRANSACTION_REJECTED);
        transaction.setTransactionProperty(TransactionProperty.SENDING_FAILED);
        user.getBuddyAccountInfo()
                .addNewTransaction(transaction);
        transactionRepository.save(transaction);
    }

    /**
     * Set an accepted transaction for operations between contacts.
     * @param senderEmail the sender's email address
     * @param receiverEmail the receiver's email address
     * @param amount amount of money sent/received
     * @param user the user to whom the transaction will be added
     *             (sender and receiver)
     * @param transactionProperty transaction property
     */
    private void acceptedTransactionBetweenContacts(
            final String senderEmail,
            final String receiverEmail,
            final Double amount,
            final User user,
            final TransactionProperty transactionProperty) {
        Transaction transaction = new Transaction();
        transaction.setSender(senderEmail);
        transaction.setRecipient(receiverEmail);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setTransactionNature(
                TransactionNature.TO_CONTACTS);
        transaction.setTransactionStatusInfo(
                TransactionStatusInfo.TRANSACTION_ACCEPTED);
        transaction.setTransactionProperty(transactionProperty);
        //ADD THE TRANSACTION TO THE USER'S BUDDY ACCOUNT INFO
        user.getBuddyAccountInfo()
                .addNewTransaction(transaction);
    }

    /**
     * Set a rejected transaction for operations between contacts.
     * @param amount amount of money
     * @param receiver the email address of user who receives money
     * @param sender the user to whom the transaction will be added
     */
    private void rejectedTransactionBetweenContacts(final Double amount,
                                                    final String receiver,
                                                    final User sender) {
        Transaction transaction = new Transaction();
        transaction.setSender(sender.getEmail());
        transaction.setRecipient(receiver);
        transaction.setTransactionDate(new Date());
        transaction.setAmount(amount);
        transaction.setTransactionNature(
                TransactionNature.TO_CONTACTS);
        transaction.setTransactionStatusInfo(
                TransactionStatusInfo.TRANSACTION_REJECTED);
        transaction.setTransactionProperty(TransactionProperty.SENDING_FAILED);
        sender.getBuddyAccountInfo()
                .addNewTransaction(transaction);
        transactionRepository.save(transaction);
    }
}
