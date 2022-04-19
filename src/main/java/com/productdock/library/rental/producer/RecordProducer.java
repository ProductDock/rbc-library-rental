package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.producer.messages.Notification;
import com.productdock.library.rental.record.RecordEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RecordProducer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord createKafkaRecord(String topic, Notification notification) {
        var serialisedMessage = serialiseMessage(notification);
        var producerRecord = new ProducerRecord<>(topic, UUID.randomUUID().toString(), serialisedMessage);
        return producerRecord;
    }

    private String serialiseMessage(Notification notification) {
        try {
            return OBJECT_MAPPER.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}