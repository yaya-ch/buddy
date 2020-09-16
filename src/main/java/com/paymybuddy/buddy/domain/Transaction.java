package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.enums.TransactionNature;
import com.paymybuddy.buddy.enums.TransactionStatusInfo;
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
@AllArgsConstructor(access = AccessLevel.PUBLIC)
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
     * The transaction id.
     * automatically generated. auto incremented
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    /**
     * The amount transferred.
     */
    @NotNull
    @Column(name = "amount")
    private Double amount;

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
}
