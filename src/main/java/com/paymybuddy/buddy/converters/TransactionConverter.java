package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.TransactionDTO;

import java.util.List;

/**
 * @author Yahia CHERIFI
 * this interface provides methods that allow
 * conversion from Transaction to TransactionDTO
 * and vice versa
 */

public interface TransactionConverter {

    /**
     * Convert Transaction entity to TransactionDTO.
     * @param transaction entity to be converted
     * @return TransactionDTO
     */
    TransactionDTO transactionEntityToTransactionDTO(Transaction transaction);

    /**
     * Convert a list of Transaction entities to a list of TransactionDTOs.
     * @param transactions a list of entities to be converted
     * @return a list of TransactionDTOs
     */
    List<TransactionDTO> transactionEntityListToTransactionDTOList(
            List<Transaction> transactions);

    /**
     * Convert TransactionDTO to Transaction entity.
     * @param transactionDTO to be converted
     * @return Transaction entity
     */
    Transaction transactionDTOToTransactionEntity(
            TransactionDTO transactionDTO);

    /**
     * Convert a list of TransactionDTOs to a list of Transaction entities.
     * @param transactionDTOS a list of DTOs to be converted
     * @return a list of Transaction entities
     */
    List<Transaction> transactionDTOListToTransactionEntityList(
            List<TransactionDTO> transactionDTOS);
}
