package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.service.RentalRecordsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class RentalsPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RentalsProducer rentalsProducer;

    @Value("${spring.kafka.topic.book-status}")
    private String kafkaTopic;

    public RentalsPublisher(KafkaTemplate<String, String> kafkaTemplate, RentalsProducer rentalsProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.rentalsProducer = rentalsProducer;
    }

    public void sendMessage(RentalRecordsMessage rentalRecordsMessage) throws ExecutionException, InterruptedException, JsonProcessingException {
        var kafkaRecord = rentalsProducer.createKafkaRecord(kafkaTopic, rentalRecordsMessage);
        kafkaTemplate.send(kafkaRecord).get();
    }
}
