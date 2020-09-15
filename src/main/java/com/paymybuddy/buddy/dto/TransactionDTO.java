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
     * The amount transferred.
     */
    @NotNull
    @NotBlank
    private Double amount;

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
}
