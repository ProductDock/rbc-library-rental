package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.record.RecordEntity;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordProducer {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord createKafkaRecord(String topic, RecordEntity recordEntity) {
        var serialisedMessage = serialiseMessage(recordEntity);
        var producerRecord = new ProducerRecord<>(topic, recordEntity.getId().toString(), serialisedMessage);
        return producerRecord;
    }

    private String serialiseMessage(RecordEntity recordEntity) {
        try {
            return OBJECT_MAPPER.writeValueAsString(recordEntity);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
