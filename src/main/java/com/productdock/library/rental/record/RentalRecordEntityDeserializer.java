package com.productdock.library.rental.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class RentalRecordEntityDeserializer {

    private final ObjectMapper objectMapper;

    public RentalRecordEntityDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RentalRecordEntity deserializeRecordEntity(ConsumerRecord<String, String> consumerRecord) {
        try {
            return objectMapper.readValue(consumerRecord.value(), RentalRecordEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new RentalRecordEntity();
    }
}
