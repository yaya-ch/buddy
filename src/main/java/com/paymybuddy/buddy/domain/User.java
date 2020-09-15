package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.enums.Civility;
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
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

/**
 * @author Yahia CHERIFI
 * this class groups all user related information.
 */

@Entity
@Table(name = "user")
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
public class User {

    /**
     * Number of maximum characters allowed in some fields.
     */
    private static final int MAX_LENGTH = 45;

    /**
     * Number of maximum characters allowed in passwords.
     */
    private static final int MAX_PASSWORD_LENGTH = 60;

    /**
     * Number of maximum characters allowed in addresses.
     */
    private static final int MAX_ADDRESS_LENGTH = 80;

    /**
     * Number of maximum characters allowed
     * in zip codes as well as in civility.
     */
    private static final int LENGTH = 5;

    /**
     * Number of maximum and maximum characters allowed in authorities.
     */
    private static final int ROLE_LENGTH = 4;

    /**
     * Number of maximum and maximum characters allowed in phone numbers.
     */
    private static final int PHONE_LENGTH = 10;

    /**
     * User's id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    /**
     * User's civility.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "civility", updatable = false, length = LENGTH)
    private Civility civility;

    /**
     * User's first name.
     */
    @NotNull
    @Column(name = "first_name", updatable = false, length = MAX_LENGTH)
    private String firstName;

    /**
     * User's last name.
     */
    @NotNull
    @Column(name = "last_name", updatable = false, length = MAX_LENGTH)
    private String lastName;

    /**
     * User's email.
     */
    @Email
    @NotNull
    @Column(name = "email", unique = true, length = MAX_LENGTH)
    private String email;

    /**
     * User's password.
     */
    @NotNull
    @Column(name = "password", length = MAX_PASSWORD_LENGTH)
    private String password;

    /**
     * A set of roles associated to a given user.
     * may be USER, ADMIN or both
     */
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id")}
    )
    private Set<Authority> authorities;

    /**
     * User's birth date.
     */
    @NotNull
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * User's address.
     */
    @NotNull
    @Column(name = "address", length = MAX_ADDRESS_LENGTH)
    private String address;

    /**
     * A city where a user lives.
     */
    @NotNull
    @Column(name = "city", length = MAX_LENGTH)
    private String city;

    /**
     * The city's zip code.
     */
    @NotNull
    @Column(name = "zip", length = LENGTH)
    private String zip;

    /**
     * User's phone number.
     */
    @NotNull
    @Column(name = "phone", length = PHONE_LENGTH)
    private String phone;

    /**
     * Information related to the user's buddy account.
     */
    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "buddy_account_info_id")
    private BuddyAccountInfo buddyAccountInfo;

    /**
     * A list of the user's contacts.
     */
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }
    )
    @JoinTable(
            name = "contacts",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "contact_id")}
    )
    private Set<User> contacts;
}
