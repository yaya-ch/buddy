package com.paymybuddy.buddy.dto;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * This dto class holds the BuddyAccountInfo related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BuddyAccountInfoDTO {

    /**
     * BuddyAccountInfo balance.
     */
    @NotNull
    @NotBlank
    private Double actualAccountBalance;

    /**
     * BuddyAccountInfo balance.
     */
    @NotNull
    @NotBlank
    private Double previousAccountBalance;

    /**
     * Information related to the associated bank account.
     */
    @NotNull
    @NotBlank
    private AssociatedBankAccountInfoDTO associatedBankAccountInfo;
}
