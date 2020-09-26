package com.paymybuddy.buddy.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yahia CHERIFI
 * This controllers exposes a landing endoint for the application
 */

@RestController
public class HomeController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(HomeController.class);

    /**
     * GET request for the main page.
     * @return a welcome message
     */
    @GetMapping({"/", "/buddy"})
    public String homePage() {
        LOGGER.debug("Get request sent from the home controller");
        return "<h1>Welcome on pay my buddy."
                + "\nThe easiest way to transfer your money</h1>";
    }
}
