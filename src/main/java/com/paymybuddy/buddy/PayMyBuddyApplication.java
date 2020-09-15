package com.paymybuddy.buddy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Yahia CHERIFI
 * The application main class
 * it is responsible for launching the app
 */
@SpringBootApplication
public class PayMyBuddyApplication {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(PayMyBuddyApplication.class);
    /**
     * The main method.
     * @param args args
     */
    public static void main(final String[] args) {
        LOGGER.debug("Buddy started");
        SpringApplication.run(PayMyBuddyApplication.class, args);
    }

    /**
     * Class constructor.
     */
    protected PayMyBuddyApplication() {
    }
}
