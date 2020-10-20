package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.constants.ColumnLength;
import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Yahia CHERIFI
 * this class groups all transaction related information.
 */

@Entity
@Table(name = "transaction")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class Transaction {

    /**
     * The transaction id.
     * automatically generated. auto incremented
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    /**
     * The user who sends money.
     */
    @NotNull
    @Column(name = "sender", length = ColumnLength.FORTY_FIVE)
    private String sender;

    /**
     * The user who receives money.
     */
    @NotNull
    @Column(name = "recipient", length = ColumnLength.FORTY_FIVE)
    private String recipient;

    /**
     * The amount transferred.
     */
    @NotNull
    @Column(name = "amount")
    private Double amount;

    /**
     * The fee that will be paid by the user who sends money.
     * Use mainly for monetizing the application
     */
    @Column(name = "fee")
    private Double fee;

    /**
     * The transaction nature.
     * either to bank account or to contacts
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_nature", length = ColumnLength.TWENTY_FIVE)
    private TransactionNature transactionNature;

    /**
     * The initial transaction status.
     * UP_COMING_TRANSACTION for recipients and SENDING_IN_PROGRESS for senders
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "initial_transaction_status_info",
            length = ColumnLength.TWENTY_FIVE)
    private TransactionStatusInfo initialTransactionStatusInfo;

    /**
     * The initial transaction status date.
     */
    @NotNull
    @Column(name = "initial_transaction_status_info_date")
    private Date initialTransactionStatusInfoDate;

    /**
     * The final transaction status.
     * TRANSACTION_ACCEPTED/TRANSACTION_REJECTED for senders or MONEY_RECEIVED
     *                                                      for recipients
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "final_transaction_status_info",
            length = ColumnLength.TWENTY_FIVE)
    private TransactionStatusInfo finalTransactionStatusInfo;

    /**
     * The final transaction status date.
     */
    @NotNull
    @Column(name = "final_transaction_status_info_date")
    private Date finalTransactionStatusInfoDate;

    /**
     * Class constructor.
     * @param moneySender the source of the transaction
     * @param moneyRecipient the user who receives money
     * @param amountOfMoney amount of money received/sent
     * @param nature transaction nature
     * @param initialStatus the initial status of a transaction
     * @param initialStatusDate transaction initialStatusDate
     * @param finalStatus the last status of a transaction
     * @param finalStatusDate final transaction status date
     */
    public Transaction(@NotNull final String moneySender,
                       @NotNull final String moneyRecipient,
                       @NotNull final Double amountOfMoney,
                       @NotNull final TransactionNature nature,
                       @NotNull final TransactionStatusInfo initialStatus,
                       @NotNull final Date initialStatusDate,
                       @NotNull final TransactionStatusInfo finalStatus,
                       @NotNull final Date finalStatusDate) {
        this.sender = moneySender;
        this.recipient = moneyRecipient;
        this.amount = amountOfMoney;
        this.transactionNature = nature;
        this.initialTransactionStatusInfo = initialStatus;
        this.initialTransactionStatusInfoDate =
                new Date(initialStatusDate.getTime());
        this.finalTransactionStatusInfo = finalStatus;
        this.finalTransactionStatusInfoDate =
                new Date(finalStatusDate.getTime());
    }

    /**
     * Getter of the initial transaction info date.
     * @return initial transaction status date
     */
    public Date getInitialTransactionStatusInfoDate() {
        if (initialTransactionStatusInfoDate == null) {
            return null;
        } else {
            return new Date(initialTransactionStatusInfoDate.getTime());
        }
    }

    /**
     * Setter of the initial transaction status date.
     * @param date date
     */
    public void setInitialTransactionStatusInfoDate(
            final Date date) {
        this.initialTransactionStatusInfoDate =
                new Date(date.getTime());
    }

    /**
     * Getter for final transaction status date.
     * @return final transaction date
     */
    public Date getFinalTransactionStatusInfoDate() {
        if (finalTransactionStatusInfoDate == null) {
            return null;
        } else {
            return new Date(finalTransactionStatusInfoDate.getTime());
        }
    }

    /**
     * Setter of the final transaction status date.
     * @param date date
     */
    public void setFinalTransactionStatusInfoDate(final Date date) {
        this.finalTransactionStatusInfoDate = new Date(date.getTime());
    }
}
