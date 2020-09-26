package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.BuddyAccountInfoConverter;
import com.paymybuddy.buddy.domain.BuddyAccountInfo;
import com.paymybuddy.buddy.dto.BuddyAccountInfoDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.service.BuddyAccountInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This controller exposes different BuddyAccountInfo related endoints
 */

@RestController
@RequestMapping("/buddyAccountInfo")
public class BuddyAccountInfoController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(BuddyAccountInfoController.class);

    /**
     * BuddyAccountInfoService to inject.
     */
    private final BuddyAccountInfoService buddyAccountInfoService;

    /**
     * BuddyAccountInfoConverter to inject.
     */
    private final BuddyAccountInfoConverter converter;

    /**
     * Constructor injection.
     * @param accountInfoService BuddyAccountInfoService
     * @param accountInfoConverter BuddyAccountInfoConverter
     */
    public BuddyAccountInfoController(
            final BuddyAccountInfoService accountInfoService,
            final BuddyAccountInfoConverter accountInfoConverter) {
        this.buddyAccountInfoService = accountInfoService;
        this.converter = accountInfoConverter;
    }

    /**
     * Find BuddyAccountInfo by id.
     * @param id BuddyAccountInfo id
     * @return BuddyAccountInfoDTO if found
     * @throws ElementNotFoundException if no element was found
     */
    @GetMapping("/getAccountInfo/{id}")
    public BuddyAccountInfoDTO findById(@PathVariable final Integer id)
            throws ElementNotFoundException {
        LOGGER.debug("Trying to load information related to account {}.", id);
        Optional<BuddyAccountInfo> findAccountById =
                buddyAccountInfoService.findById(id);
        if (findAccountById.isPresent()) {
            LOGGER.info("Information related to account"
                    + " {} loaded successfully.", id);
            return converter.buddyAccountInfoEntityToDTO(findAccountById.get());
        } else {
            LOGGER.error("Failed to load information related to account {}."
                    + " No matching account was found.", id);
            throw new ElementNotFoundException(
                    "No matching id was found in database.");
        }
    }
}
