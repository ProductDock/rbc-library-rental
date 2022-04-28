package com.productdock.library.rental.kafka;

import com.productdock.library.rental.service.RentalRecordsMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class RentalsPublisher {

    private final KafkaTemplate kafkaTemplate;
    private final RecordProducer recordProducer;

    @Value("${spring.kafka.topic.rental-record-topic}")
    private String KAFKA_TOPIC;

    public RentalsPublisher(KafkaTemplate kafkaTemplate, RecordProducer recordProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.recordProducer = recordProducer;
    }

    public void sendMessage(RentalRecordsMessage rentalRecordsMessage) throws Exception {
        var kafkaRecord = recordProducer.createKafkaRecord(KAFKA_TOPIC, rentalRecordsMessage);
        kafkaTemplate.send(kafkaRecord).get();
    }
}
