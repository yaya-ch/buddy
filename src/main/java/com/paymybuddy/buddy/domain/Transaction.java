package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.constants.ConstantNumbers;
import com.paymybuddy.buddy.enums.TransactionNature;
import lombok.AllArgsConstructor;
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
import javax.persistence.OneToOne;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Embedded;
import javax.persistence.AttributeOverride;

import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * this class groups all transaction related information.
 */

@Entity
@Table(name = "transaction")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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
    @OneToOne
    private BuddyAccountInfo sender;

    /**
     * The user who receives money.
     */
    @NotNull
    @OneToOne
    private BuddyAccountInfo recipient;

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
     * A message that describes the transaction.
     */
    @NotNull
    @Column(name = "description",
            length = ConstantNumbers.MAX_DESCRIPTION_CHARACTERS)
    private String description;

    /**
     * The transaction nature.
     * either to bank account or to contacts
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_nature", length = ConstantNumbers.TWENTY_FIVE)
    private TransactionNature transactionNature;

    /**
     * Provides information about transactions.
     */
    @Embedded
    @AttributeOverride(name = "initialTransactionStatusInfo",
            column = @Column(name = "initial_transaction_status_info"))
    @AttributeOverride(name = "initialTransactionStatusInfoDate",
            column = @Column(name =
                    "initial_transaction_status_info_date"))
    @AttributeOverride(name = "finalTransactionStatusInfo",
            column = @Column(name = "final_transaction_status_info"))
    @AttributeOverride(name = "finalTransactionStatusInfoDate",
            column = @Column(name =
                    "final_transaction_status_info_date"))
    private TransactionStatus transactionStatus;
}
