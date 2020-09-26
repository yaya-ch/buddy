package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Yahia CHERIFI
 * this class holds contact related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class ContactDTO {

    /**
     * The contact (user) first name.
     */
    @NotNull
    @NotBlank
    private String firstName;

    /**
     * The contact (user) last name.
     */
    @NotNull
    @NotBlank
    private String lastName;

    /**
     * The contact (user) email address.
     */
    @NotNull
    @NotBlank
    private String email;
}
