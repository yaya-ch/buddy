package com.paymybuddy.buddy.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Yahia CHERIFI
 * This is a  security configuration class
 */

@EnableWebSecurity
@Configuration
public class BuddySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Allows configuring web based security for specific http requests.
     * @param http HttpSecurity
     * @throws Exception if any problem occurs
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/buddy").permitAll();
    }
}
