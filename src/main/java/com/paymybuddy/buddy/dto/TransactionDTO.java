package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * this class holds the transaction related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class TransactionDTO {

    /**
     * the source of the transaction.
     */
    @NotNull
    @NotBlank
    private BuddyAccountInfoTransactionsDTO sender;

    /**
     * the user who receives money.
     */
    @NotNull
    @NotBlank
    private BuddyAccountInfoTransactionsDTO recipient;
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
     * A message used to describe the transaction.
     */
    private String description;

    /**
     * The transaction nature.
     * either to bank account or to contacts
     */
    @NotNull
    @NotBlank
    private String transactionNature;

    /**
     *
     */
    @NotNull
    @NotBlank
    private TransactionStatusDTO transactionStatus;
}
