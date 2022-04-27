package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.service.RentalRecordEntity;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public record RentalRecordEntityDeserializer(ObjectMapper objectMapper) {

    public RentalRecordEntity deserializeRecordEntity(ConsumerRecord<String, String> consumerRecord) {
        try {
            return objectMapper.readValue(consumerRecord.value(), RentalRecordEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new RentalRecordEntity();
    }
}
