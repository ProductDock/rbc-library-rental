package com.productdock.library.rental.producer;

import com.productdock.library.rental.record.RecordEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Publisher {

    private final KafkaTemplate kafkaTemplate;
    private final RecordProducer recordProducer;

    @Value("${spring.kafka.topic.notifications-topic}")
    private String KAFKA_TOPIC;

    public Publisher(KafkaTemplate kafkaTemplate, RecordProducer recordProducer) {
        this.kafkaTemplate = kafkaTemplate;
        this.recordProducer = recordProducer;
    }

    public void sendMessage(RecordEntity recordEntity) {
        var kafkaRecord = recordProducer.createKafkaRecord(KAFKA_TOPIC, recordEntity);
        try {
            var resp = kafkaTemplate.send(kafkaRecord).get();
            System.out.print(resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
