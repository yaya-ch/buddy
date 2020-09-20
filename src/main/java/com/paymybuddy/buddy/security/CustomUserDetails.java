package com.paymybuddy.buddy.security;

import com.paymybuddy.buddy.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yahia CHERIFI
 * This class provides core user information
 */
public class CustomUserDetails implements UserDetails {

    /**
     * User name: email.
     */
    private final String userName;

    /**
     * User password.
     */
    private final String password;

    /**
     * User roles.
     */
    private final List<GrantedAuthority> authorities;

    /**
     * Class constructor.
     * @param user user
     */
    public CustomUserDetails(final User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.authorities = Arrays.stream(user.getRole().toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    /**
     * Getter for authorities.
     * @return the authorities granted to the user
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Getter for password.
     * @return the password used to authenticate the user
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Getter for userName.
     * @return  the username used to authenticate the user(the user's email)
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * Indicates whether the user's account has expired.
     * @return true by default
     * DO NOT USE THIS IN PROD ENV
     * THIS PROPERTY SHOULD BE IMPLEMENTED TO SUPPORT FALSE
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked.
     * @return true by default
     * DO NOT USE THIS IN PROD ENV
     * THIS PROPERTY SHOULD BE IMPLEMENTED TO SUPPORT FALSE
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials has expired.
     * @return true by default
     * DO NOT USE THIS IN PROD ENV
     * THIS PROPERTY SHOULD BE IMPLEMENTED TO SUPPORT FALSE
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled.
     * @return true by default
     * DO NOT USE THIS IN PROD ENV
     * THIS PROPERTY SHOULD BE IMPLEMENTED TO SUPPORT FALSE
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
