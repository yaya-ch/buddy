package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Yahia CHERIFI
 * this class holds the transaction related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TransactionDTO {

    /**
     * The email or iban of the money sender.
     */
    @NotNull
    @NotBlank
    private String sender;

    /**
     * The email or iban of the money receiver.
     */
    @NotNull
    @NotBlank
    private String recipient;
    /**
     * The amount transferred.
     */
    @NotNull
    @NotBlank
    private Double amount;

    /**
     * The that will be paid by the user who sends money.
     * Used mainly for monetizing the application
     */
    private Double fee;

    /**
     * The transaction date.
     */
    @NotNull
    @NotBlank
    private Date transactionDate;

    /**
     * The transaction nature.
     * either to bank account or to contacts
     */
    @NotNull
    @NotBlank
    private String transactionNature;

    /**
     * The transaction status.
     * Accepted or rejected
     */
    @NotNull
    @NotBlank
    private String transactionStatusInfo;

    /**
     * transactionDate getter.
     * @return transactionDate
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
     * @param date transactionDate
     */
    public void setTransactionDate(final Date date) {
        this.transactionDate = new Date(date.getTime());
    }
}
