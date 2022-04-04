package com.tosan.deposit.repositories;

import java.util.Optional;

import com.tosan.deposit.dto.TransactionDto;

public interface TransactionsResources {
    public Optional<TransactionDto> registerTransaction(TransactionDto transaction);
    
}
