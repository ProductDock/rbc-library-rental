package com.productdock.library.rental.producer;

import com.productdock.library.rental.record.RentalRecordEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final KafkaTemplate kafkaTemplate;
    private final RecordProducer recordProducer;

    @Value("${spring.kafka.topic.rental-record-topic}")
    private String KAFKA_TOPIC;

    public Publisher(KafkaTemplate kafkaTemplate, RecordProducer recordProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.recordProducer = recordProducer;
    }

    public void sendMessage(RentalRecordEntity rentalRecordEntity) {
        try {
            var kafkaRecord = recordProducer.createKafkaRecord(KAFKA_TOPIC, rentalRecordEntity);
            kafkaTemplate.send(kafkaRecord).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
