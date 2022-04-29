package com.productdock.library.rental.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.service.FailedRequest;
import com.productdock.library.rental.service.RentalRecordService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public record FailedRequestConsumer(RentalRecordService rentalRecordService,
                                    FailedRequestDeserializer failedRequestDeserializer) {

    @KafkaListener(topics = "${spring.kafka.topic.bad-rental-request}")
    public synchronized void listen(ConsumerRecord<String, String> message) throws JsonProcessingException {
        FailedRequest failedRequest = failedRequestDeserializer.deserializeFailedRequest(message);
        rentalRecordService.processFailedRequest(failedRequest);
    }
}
