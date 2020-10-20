package com.paymybuddy.buddy.domain;

import com.paymybuddy.buddy.constants.ColumnLength;
import com.paymybuddy.buddy.enums.Role;
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
    @Column(name = "civility", updatable = false, length = ColumnLength.FIVE)
    private Civility civility;

    /**
     * User's first name.
     */
    @NotNull
    @Column(name = "first_name", updatable = false,
            length = ColumnLength.FORTY_FIVE)
    private String firstName;

    /**
     * User's last name.
     */
    @NotNull
    @Column(name = "last_name", updatable = false,
            length = ColumnLength.FORTY_FIVE)
    private String lastName;

    /**
     * User's email.
     */
    @Email
    @NotNull
    @Column(name = "email", unique = true, length = ColumnLength.FORTY_FIVE)
    private String email;

    /**
     * User's password.
     */
    @NotNull
    @Column(name = "password", length = ColumnLength.SIXTY)
    private String password;

    /**
     * The user's role: Admin or user.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", updatable = false, length = ColumnLength.TEN)
    private Role role;

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
    @Column(name = "address", length = ColumnLength.EIGHTY)
    private String address;

    /**
     * A city where a user lives.
     */
    @NotNull
    @Column(name = "city", length = ColumnLength.FORTY_FIVE)
    private String city;

    /**
     * The city's zip code.
     */
    @NotNull
    @Column(name = "zip", length = ColumnLength.FIVE)
    private String zip;

    /**
     * User's phone number.
     */
    @NotNull
    @Column(name = "phone", length = ColumnLength.TEN)
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
