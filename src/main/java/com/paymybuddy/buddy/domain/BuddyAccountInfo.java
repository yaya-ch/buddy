package com.paymybuddy.buddy.domain;

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
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Yahia CHERIFI
 * This class groups all information related to the buddy account.
 */

@Entity
@Table(name = "buddy_account_info")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class BuddyAccountInfo {

    /**
     * BuddyAccountInfo id.
     * automatically generated. auto incremented
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buddy_account_info_id")
    private Integer buddyAccountInfoId;

    /**
     * BuddyAccountInfo actual balance.
     */
    @NotNull
    @Column(name = "actual_account_balance")
    private Double actualAccountBalance;

    /**
     * BuddyAccountInfo previous balance(after the last transaction).
     */
    @NotNull
    @Column(name = "previous_account_balance")
    private Double previousAccountBalance;

    /**
     * Information related to the associated bank account.
     */
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "associated_bank_account_info_id")
    private AssociatedBankAccountInfo associatedBankAccountInfo;

    /**
     * A list of the different transactions.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "buddy_account_info_id")
    private Set<Transaction> transactions;

    /**
     * Add a new transaction to the list of transactions.
     * @param transaction the transaction to add
     */
    public void addNewTransaction(final Transaction transaction) {
        transactions.add(transaction);
    }
}
