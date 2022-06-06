package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.service.RentalRecordsMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class KafkaMessageProducer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord<String, String> createKafkaRecord(String topic, RentalRecordsMessage rentalRecordsMessage) throws JsonProcessingException {
        log.debug("Create kafka record on topic {} with message: {}", topic, rentalRecordsMessage);
        var serialisedMessage = serialiseMessage(rentalRecordsMessage);
        return new ProducerRecord<>(topic, UUID.randomUUID().toString(), serialisedMessage);
    }

    private String serialiseMessage(RentalRecordsMessage rentalRecordsMessage) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(rentalRecordsMessage);
    }
}
