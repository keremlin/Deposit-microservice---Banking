package com.tosan.deposit.kafka;

import com.tosan.deposit.dto.TransactionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Profile("EnableKafka")
@Slf4j
@Service
@EnableKafka
public class KafkaProducer {

    public static final String TOPIC = "transactions";
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, TransactionDto> objectKafkaTemplate;

    public void sendMessage(TransactionDto message) {
        log.info("the message in object is : {}", message);
        var future = objectKafkaTemplate.send(TOPIC, message);
        future.whenComplete((x, y) -> {
            log.info("message : {} --> partirion : {}",
                    x.getRecordMetadata().timestamp(), x.getRecordMetadata().partition());
        });
    }

    public void sendMessage(String msg) {

        var future = kafkaTemplate.send(TOPIC, msg);
        future.whenComplete((x, y) -> {
            log.info("The message is sent successfully : {} , {}", x.toString(), y.toString());
        });
        while(!future.isDone()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("Wait for sending message !");
        }
    }
}
