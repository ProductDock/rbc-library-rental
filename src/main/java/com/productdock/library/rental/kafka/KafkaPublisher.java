package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.service.RentalRecordsMessage;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaMessageProducer kafkaMessageProducer;

    public KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate, KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    public void sendMessage(RentalRecordsMessage rentalRecordsMessage, String kafkaTopic) throws ExecutionException, InterruptedException, JsonProcessingException {
        var kafkaRecord = kafkaMessageProducer.createKafkaRecord(kafkaTopic, rentalRecordsMessage);
        kafkaTemplate.send(kafkaRecord).get();
    }
}
