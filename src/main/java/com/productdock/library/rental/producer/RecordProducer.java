package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.record.RentalRecordEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RecordProducer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord createKafkaRecord(String topic, RentalRecordEntity rentalRecordEntity) throws JsonProcessingException {
        var serialisedMessage = serialiseMessage(rentalRecordEntity);
        var producerRecord = new ProducerRecord<>(topic, UUID.randomUUID().toString(), serialisedMessage);
        return producerRecord;
    }

    private String serialiseMessage(RentalRecordEntity rentalRecordEntity) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(rentalRecordEntity);
    }
}
