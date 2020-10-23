package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Yahia CHERIFI
 * This DTO class is used to return the the money sender's/recipient's id
 * To avoid returninig all the information related to a given buddy account
 *
 */
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class BuddyAccountInfoTransactionsDTO {

    /**
     * BuddyAccountInfo id.
     * Used to display the sender/recipient.
     */
    private Integer buddyAccountInfoId;
}
