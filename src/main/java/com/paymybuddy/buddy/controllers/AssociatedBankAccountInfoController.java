package com.paymybuddy.buddy.controllers;

import com.paymybuddy.buddy.converters.AssociatedBankAccountInfoConverter;
import com.paymybuddy.buddy.domain.AssociatedBankAccountInfo;
import com.paymybuddy.buddy.dto.AssociatedBankAccountInfoDTO;
import com.paymybuddy.buddy.exceptions.ElementNotFoundException;
import com.paymybuddy.buddy.service.AssociatedBankAccountInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * @author Yahia CHERIFI
 * This controller exposes some AssociatedBankAccountInfo endpoints
 */

@RestController
@RequestMapping("/bankAccountInfo")
public class AssociatedBankAccountInfoController {

    /**
     * Class logger.
     */
    private static final Logger LOGGER =
            LogManager.getLogger(AssociatedBankAccountInfoController.class);

    /**
     * AssociatedBankAccountInfoService to be injected.
     */
    private final AssociatedBankAccountInfoService bankAccountInfoService;

    /**
     * AssociatedBankAccountInfoConverter to inject.
     */
    private final AssociatedBankAccountInfoConverter converter;

    /**
     * ModelMapper to inject.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param service AssociatedBankAccountInfoService
     * @param bankAccountInfoConverter AssociatedBankAccountInfoConverter
     * @param modelMapper model mapper
     */
    @Autowired
    public AssociatedBankAccountInfoController(
            final AssociatedBankAccountInfoService service,
            final AssociatedBankAccountInfoConverter bankAccountInfoConverter,
            final ModelMapper modelMapper) {
        this.bankAccountInfoService = service;
        this.converter = bankAccountInfoConverter;
        this.mapper = modelMapper;
    }

    /**
     * Find AssociatedBankAccountInfo by id.
     * @param id the AssociatedBankAccountInfo id
     * @return AssociatedBankAccountInfoDTO if found
     * @throws ElementNotFoundException if no matching id was found
     */
    @GetMapping("/findById/{id}")
    public AssociatedBankAccountInfoDTO findById(
            @PathVariable final Integer id) throws ElementNotFoundException {
        LOGGER.debug("Loading information related to bank account {}", id);
        Optional<AssociatedBankAccountInfo> checkForExistingBankAccount =
                bankAccountInfoService.findById(id);
        if (checkForExistingBankAccount.isPresent()) {
            LOGGER.info("Information related to bank account"
                    + " {} loaded successfully.", id);
            return converter.associatedBankAccountInfoToDTO(
                    checkForExistingBankAccount.get());
        } else {
            LOGGER.error("Failed to load information related to bank account"
                    + " {}. No matching id was found.", id);
            throw new ElementNotFoundException(
                    "Failed to load bank account information."
                    + " No matching id was found: " + id);
        }
    }

    /**
     * Update an existing associated bank account information.
     * @param bankAccountInfoDTO contains the new information to persist
     * @param id the account id
     * @return success message
     */
    @PutMapping("/update/{id}")
    public String updateBankAccountInfo(
            @RequestBody final AssociatedBankAccountInfoDTO bankAccountInfoDTO,
            @PathVariable final Integer id) throws ElementNotFoundException {
        LOGGER.debug("Trying to update information"
                + " related bank account {}", id);
        Optional<AssociatedBankAccountInfo> checkForExistingBankAccount =
                bankAccountInfoService.findById(id);
        if (checkForExistingBankAccount.isPresent()) {
            LOGGER.debug("Updating information related to bank account {}", id);
            mapper.map(bankAccountInfoDTO, checkForExistingBankAccount.get());
            bankAccountInfoService
                    .updateBankAccountInfo(checkForExistingBankAccount.get());
            LOGGER.info("Information related to bank account"
                    + " {} updated successfully", id);
            return "Bank account information updated successfully.";
        } else {
            LOGGER.error("Failed to update information"
                    + " related to bank account {}."
                    + " No matching account was found.", id);
            throw new ElementNotFoundException(
                    "Failed to update information related"
                    + " to bank account. No matching id was found " + id);
        }
    }
}
