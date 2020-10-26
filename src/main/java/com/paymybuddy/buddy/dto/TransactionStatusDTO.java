package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TransactionStatusDTO {

    /**
     * The initial transaction status.
     * Usually TRANSACTION_IN_PROGRESS
     */
    @NotNull
    @NotBlank
    private String initialTransactionStatusInfo;

    /**
     * The initial transaction status date.
     */
    @NotNull
    @NotBlank
    private Date initialTransactionStatusInfoDate;

    /**
     * The final transaction status.
     * TRANSACTION_ACCEPTED/TRANSACTION_REJECTED
     */
    @NotNull
    @NotBlank
    private String finalTransactionStatusInfo;

    /**
     * The final transaction status date.
     */
    @NotNull
    @NotBlank
    private Date finalTransactionStatusInfoDate;

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
    public void setInitialTransactionStatusInfoDate(final Date date) {
        this.initialTransactionStatusInfoDate = new Date(date.getTime());
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
