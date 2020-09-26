package com.paymybuddy.buddy.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yahia CHERIFI
 * This is a configuration class
 */

@Configuration
public class BuddyConfig {

    /**
     * ModelMapper bean.
     * used mainly in the conversion process
     * from DTO to entity and vice versa
     * @return new ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
