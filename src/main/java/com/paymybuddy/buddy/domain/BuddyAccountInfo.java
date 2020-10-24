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
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;

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
}
