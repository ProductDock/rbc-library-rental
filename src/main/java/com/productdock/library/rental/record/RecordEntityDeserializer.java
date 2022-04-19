package com.productdock.library.rental.record;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public class RecordEntityDeserializer {

    private final ObjectMapper objectMapper;

    public RecordEntityDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public RecordEntity deserializeRecordEntity(ConsumerRecord<String, String> consumerRecord) {
        try {
            return objectMapper.readValue(consumerRecord.value(), RecordEntity.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new RecordEntity();
    }
}
