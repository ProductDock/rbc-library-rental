package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.service.RentalRecordsMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public record RentalRecordsMessageDeserializer(ObjectMapper objectMapper) {

    public RentalRecordsMessage deserializeRentalRecordsMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        return objectMapper.readValue(consumerRecord.value(), RentalRecordsMessage.class);
    }
}