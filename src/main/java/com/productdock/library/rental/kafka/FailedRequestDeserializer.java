package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.service.FailedRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;

@Component
public record FailedRequestDeserializer(ObjectMapper objectMapper) {

    public FailedRequest deserializeFailedRequest(ConsumerRecord<String, String> consumerRecord) {
        try {
            return objectMapper.readValue(consumerRecord.value(), FailedRequest.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new FailedRequest();
    }
}
