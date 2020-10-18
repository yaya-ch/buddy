package com.paymybuddy.buddy.domain;

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
     * The maximum number of characters allowed in transactionNature.
     */
    private static final int LENGTH = 25;

    /**
     * The maximum number of characters allowed in sender/recipient.
     */
    private static final int MAX_LENGTH = 45;

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
    @Column(name = "sender", length = MAX_LENGTH)
    private String sender;

    /**
     * The user who receives money.
     */
    @NotNull
    @Column(name = "recipient", length = MAX_LENGTH)
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
     * The transaction date.
     */
    @NotNull
    @Column(name = "transaction_date")
    private Date transactionDate;

    /**
     * The transaction nature.
     * either to bank account or to contacts
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_nature", length = LENGTH)
    private TransactionNature transactionNature;

    /**
     * The transaction status.
     * Accepted or rejected for some reason
     */
    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "transaction_status_info", length = LENGTH)
    private TransactionStatusInfo transactionStatusInfo;

    /**
     * Class constructor.
     * @param moneySender the source of the transaction
     * @param moneyRecipient the user who receives money
     * @param amountOfMoney amount of money
     * @param date transaction date
     * @param nature transaction nature
     * @param transactionStatus transaction status
     */
    public Transaction(@NotNull final String moneySender,
                       @NotNull final String moneyRecipient,
                       @NotNull final Double amountOfMoney,
                       @NotNull final Date date,
                       @NotNull final TransactionNature nature,
                       @NotNull final TransactionStatusInfo transactionStatus) {
        this.sender = moneySender;
        this.recipient = moneyRecipient;
        this.amount = amountOfMoney;
        this.transactionDate = new Date(date.getTime());
        this.transactionNature = nature;
        this.transactionStatusInfo = transactionStatus;
    }

    /**
     * transactionDate getter.
     * @return transaction date
     */
    public Date getTransactionDate() {
        if (transactionDate == null) {
            return null;
        } else {
            return new Date(transactionDate.getTime());
        }
    }

    /**
     * transactionDate setter.
     * @param date transaction date
     */
    public void setTransactionDate(final Date date) {
        this.transactionDate = new Date(date.getTime());
    }
}
