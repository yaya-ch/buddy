package com.paymybuddy.buddy.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Yahia CHERIFI
 * This dto class holds the user related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class UserDTO {

    /**
     * The user's civility.
     */
    @NotNull
    @NotBlank
    private String civility;

    /**
     * The user's first name.
     */
    @NotNull
    @NotBlank
    private String firstName;

    /**
     * The user's last name.
     */
    @NotNull
    @NotBlank
    private String lastName;

    /**
     * The user's email address.
     */
    @NotNull
    @NotBlank
    private String email;

    /**
     * The user's password.
     */
    @NotNull
    @NotBlank
    private String password;

    /**
     * The user's authorities(roles).
     */
    @NotNull
    @NotBlank
    private Set<AuthorityDTO> authorities;

    /**
     * The user's birth date.
     */
    @NotNull
    @NotBlank
    private LocalDate birthDate;

    /**
     * The user's address.
     */
    @NotNull
    @NotBlank
    private String address;

    /**
     * The city where a user lives.
     */
    @NotNull
    @NotBlank
    private String city;

    /**
     * The city's zip code .
     */
    @NotNull
    @NotBlank
    private String zip;

    /**
     * The user's phone number.
     */
    @NotNull
    @NotBlank
    private String phone;

    /**
     * Information related to the user's buddy account.
     */
    @NotNull
    @NotBlank
    private BuddyAccountInfoDTO buddyAccountInfo;

    /**
     * A list of the user's contacts.
     */
    private Set<ContactDTO> contacts;
}
