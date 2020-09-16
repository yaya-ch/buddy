package com.paymybuddy.buddy.dto;

import lombok.NoArgsConstructor;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Yahia CHERIFI
 * This dto class holds the authority related data
 */

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
public class AuthorityDTO {

    /**
     * The role name: may be USER or ADMIN.
     */
    @NotNull
    @NotBlank
    private String name;

    /**
     * A List of users associated to a given role.
     */
    private List<UserDTO> users;
}
