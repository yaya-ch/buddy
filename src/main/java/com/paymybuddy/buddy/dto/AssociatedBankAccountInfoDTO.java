package com.paymybuddy.buddy.dto;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * This dto class holds the AssociatedBankAccountInfo related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class AssociatedBankAccountInfoDTO {

    /**
     * Bank account holder first name.
     */
    @NotNull
    @NotBlank
    private String bankAccountHolderFirstName;

    /**
     * Bank account holder last name.
     */
    @NotNull
    @NotBlank
    private String bankAccountHolderLastName;

    /**
     * Associated bank account iban.
     * International Bank Account Number
     */
    @NotNull
    @NotBlank
    private String iban;

    /**
     * Associated bank account bic.
     * Bank Identifier Code
     */
    @NotNull
    @NotBlank
    private String bic;
}
