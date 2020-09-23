package com.paymybuddy.buddy.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Yahia CHERIFI
 * This is a security configuration class
 */

@EnableWebSecurity
@Configuration
public class BuddySecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * BCryptPassWord encoding strength.
     */
    private static final int PASSWORD_ENCODING_STRENGTH = 15;

    /**
     * UserDetailsService to be injected.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Constructor injection.
     * @param userDetails UserDetailsService instance
     */
    @Autowired
    public BuddySecurityConfig(@Qualifier(
            "userDetailsService") final UserDetailsService userDetails) {
        this.userDetailsService = userDetails;
    }

    /**
     * Allows building UserDetailsService based authentication.
     * @param auth AuthenticationManagerBuilder
     * @throws Exception if an error occurs when adding
     * the UserDetailsService based authentication
     */
    @Override
    protected void configure(
            final AuthenticationManagerBuilder auth)throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    /**
     * Allows configuring web based security for specific http requests.
     * @param http HttpSecurity
     * @throws Exception if any problem occurs
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin", "/delete", "/findAll")
                .hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
                .antMatchers("/", "/buddy", "/signup",
                        "/update/**", "/delete/**", "/buddyAccountInfo/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().formLogin();
    }

    /**
     * Password encoder bean.
     * @return an instance of NoOpPasswordEncoder(to be updated by bcrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(PASSWORD_ENCODING_STRENGTH);
    }
}
