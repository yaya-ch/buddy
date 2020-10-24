package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.enums.TransactionStatusInfo;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.ToString;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Yahia CHERIFI
 * This class is embedded by the Transaction entity.
 * It holds transaction status information
 */

@Embeddable
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class TransactionStatus {

    /**
     * Refers to to the initial transaction status.
     * Usually TRANSACTION_IN_PROGRESS
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionStatusInfo initialTransactionStatusInfo;

    /**
     * Refers to the date in which the initial transaction status occurs.
     */
    @NotNull
    private Date initialTransactionStatusInfoDate;

    /**
     * Refers to the final transaction status.
     * Usually TRANSACTION_ACCEPTED or TRANSACTION_REJECTED
     */
    @NotNull
    @Enumerated(EnumType.STRING)

    private TransactionStatusInfo finalTransactionStatusInfo;

    /**
     * Refers to the date in which the final transaction status occurs.
     */
    @NotNull
    private Date finalTransactionStatusInfoDate;

    /**
     * Class constructor.
     * @param initialTransactionStatus the initial status of a transaction
     * @param initialTransactionStatusDate date of the initial status
     * @param finalTransactionStatus the last status of a transaction
     * @param finalTransactionStatusDate date of the final status
     */
    public TransactionStatus(
            final TransactionStatusInfo initialTransactionStatus,
            final Date initialTransactionStatusDate,
            final TransactionStatusInfo finalTransactionStatus,
            final Date finalTransactionStatusDate) {
        this.initialTransactionStatusInfo = initialTransactionStatus;
        this.initialTransactionStatusInfoDate =
                new Date(initialTransactionStatusDate.getTime());
        this.finalTransactionStatusInfo = finalTransactionStatus;
        this.finalTransactionStatusInfoDate =
                new Date(finalTransactionStatusDate.getTime());
    }

    /**
     * Getter of the initialTransactionStatusInfoDate.
     * @return initialTransactionStatusInfoDate
     */
    public Date getInitialTransactionStatusInfoDate() {
        if (initialTransactionStatusInfoDate == null) {
            return null;
        } else {
            return new Date(initialTransactionStatusInfoDate.getTime());
        }
    }

    /**
     * Setter of the initialTransactionStatusDate.
     * @param initialTransactionStatusDate the date
     *                       in which the initialTransactionStatusDate occurs
     */
    public void setInitialTransactionStatusInfoDate(
            final Date initialTransactionStatusDate) {
        this.initialTransactionStatusInfoDate =
                new Date(initialTransactionStatusDate.getTime());
    }

    /**
     * Getter of the finalTransactionStatusInfoDate.
     * @return finalTransactionStatusInfoDate
     */
    public Date getFinalTransactionStatusInfoDate() {
        if (finalTransactionStatusInfoDate == null) {
            return null;
        } else {
            return new Date(finalTransactionStatusInfoDate.getTime());
        }
    }

    /**
     * Setter of the finalTransactionStatusInfoDate.
     * @param finalTransactionStatusDate date in which
     *                                the finalTransactionStatusInfoDate occurs
     */
    public void setFinalTransactionStatusInfoDate(
            final Date finalTransactionStatusDate) {
        this.finalTransactionStatusInfoDate =
                new Date(finalTransactionStatusDate.getTime());
    }
}
