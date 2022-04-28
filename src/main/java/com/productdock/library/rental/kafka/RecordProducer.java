package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordsMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RecordProducer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord createKafkaRecord(String topic, RentalRecordsMessage rentalRecordsMessage) throws JsonProcessingException {
        var serialisedMessage = serialiseMessage(rentalRecordsMessage);
        var producerRecord = new ProducerRecord<>(topic, UUID.randomUUID().toString(), serialisedMessage);
        return producerRecord;
    }

    private String serialiseMessage(RentalRecordsMessage rentalRecordsMessage) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(rentalRecordsMessage);
    }
}
