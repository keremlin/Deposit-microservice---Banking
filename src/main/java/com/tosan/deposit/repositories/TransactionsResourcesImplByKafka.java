package com.tosan.deposit.repositories;

import com.tosan.deposit.dto.TransactionDto;
import com.tosan.deposit.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Profile("EnableKafka")
@Slf4j
@Service
public class TransactionsResourcesImplByKafka implements TransactionsResources {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Override
    public Optional<TransactionDto> registerTransaction(TransactionDto transaction) {
        try {
            kafkaProducer.sendMessage(transaction);
            return Optional.of(transaction);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Optional.empty();
        }
    }
}
