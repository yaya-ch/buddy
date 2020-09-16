package com.paymybuddy.buddy.converters;

import com.paymybuddy.buddy.domain.Transaction;
import com.paymybuddy.buddy.dto.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yahia CHERIFI
 * A class that implements TransactionConverter interface
 * @see TransactionConverter
 */
@Component
public class TransactionConverterImpl implements TransactionConverter {

    /**
     * ModelMapper to be injected.
     */
    private final ModelMapper mapper;

    /**
     * Constructor injection.
     * @param modelMapper ModelMapper instance
     */
    public TransactionConverterImpl(
            final ModelMapper modelMapper) {
        this.mapper = modelMapper;
    }

    /**
     * @param transaction entity to be converted
     * @return TransactionDTO
     */
    @Override
    public TransactionDTO transactionEntityToTransactionDTO(
            final Transaction transaction) {
        return mapper.map(transaction, TransactionDTO.class);
    }

    /**
     * @param transactions a list of entities to be converted
     * @return a list of TransactionDTOs
     */
    @Override
    public List<TransactionDTO> transactionEntityListToTransactionDTOList(
            final List<Transaction> transactions) {
        return transactions.stream()
                .map(this::transactionEntityToTransactionDTO)
                .collect(Collectors.toList());
    }

    /**
     * @param transactionDTO to be converted
     * @return Transaction entity
     */
    @Override
    public Transaction transactionDTOToTransactionEntity(
            final TransactionDTO transactionDTO) {
        return mapper.map(transactionDTO, Transaction.class);
    }

    /**
     * @param transactionDTOS a list of DTOs to be converted
     * @return a list of Transaction entities
     */
    @Override
    public List<Transaction> transactionDTOListToTransactionEntityList(
            final List<TransactionDTO> transactionDTOS) {
        return transactionDTOS.stream()
                .map(this::transactionDTOToTransactionEntity)
                .collect(Collectors.toList());
    }
}
