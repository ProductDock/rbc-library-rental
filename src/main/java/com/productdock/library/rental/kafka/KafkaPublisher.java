package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.service.RentalRecordsMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
public class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaMessageProducer kafkaMessageProducer;

    public KafkaPublisher(KafkaTemplate<String, String> kafkaTemplate, KafkaMessageProducer kafkaMessageProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaMessageProducer = kafkaMessageProducer;
    }

    public void sendMessage(RentalRecordsMessage rentalRecordsMessage, String kafkaTopic) throws ExecutionException, InterruptedException, JsonProcessingException {
        log.debug("Sent kafka message: {} on kafka topic: {}", rentalRecordsMessage, kafkaTopic);
        var kafkaRecord = kafkaMessageProducer.createKafkaRecord(kafkaTopic, rentalRecordsMessage);
        kafkaTemplate.send(kafkaRecord).get();
    }
}
