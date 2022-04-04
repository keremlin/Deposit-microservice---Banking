package com.tosan.deposit.repositories;

import java.util.Optional;

import com.tosan.deposit.dto.TransactionDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionsResourcesImpl implements TransactionsResources{
    
    @Autowired
    private RestTemplate rest;

    @Override
    public Optional<TransactionDto> registerTransaction(TransactionDto transaction) {
        try{
            return Optional.of( rest.postForObject("http://localhost:8070/api/transaction/save", transaction,TransactionDto.class));
        }catch(Exception ex){
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }
    
}
