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
import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * This class gathers the information related
 * to the Buddy's associated bank account
 */

@Entity
@Table(name = "associated_bank_account_info")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class AssociatedBankAccountInfo {

    /**
     * The number of maximum characters allowed in some fields.
     */
    private static final int LENGTH = 45;

    /**
     * The number of maximum characters allowed IBANs.
     */
    private static final int IBAN_LENGTH = 34;

    /**
     * The number of maximum characters allowed in BICs.
     */
    private static final int BIC_LENGTH = 15;
    /**
     * AssociatedBankAccountInfo id.
     * automatically generated. auto incremented
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "associated_bank_account_info_id")
    private Integer associatedBankAccountInfoId;

    /**
     * Bank account holder first name.
     */
    @NotNull
    @Column(name = "bank_account_holder_first_name",
            updatable = false, length = LENGTH)
    private String bankAccountHolderFirstName;

    /**
     * Bank account holder last name.
     */
    @NotNull
    @Column(name = "bank_account_holder_last_name",
            updatable = false, length = LENGTH)
    private String bankAccountHolderLastName;

    /**
     * Associated bank account iban.
     * International Bank Account Number
     */
    @NotNull
    @Column(name = "iban", unique = true, length = IBAN_LENGTH)
    private String iban;

    /**
     * Associated bank account bic.
     * Bank Identifier Code
     */
    @NotNull
    @Column(name = "bic", unique = true, length = BIC_LENGTH)
    private String bic;
}
