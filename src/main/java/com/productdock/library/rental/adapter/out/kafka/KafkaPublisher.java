package com.productdock.library.rental.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.domain.RentalRecordsMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
class KafkaPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaMessageProducer kafkaMessageProducer;

    public void sendMessage(RentalRecordsMessage rentalRecordsMessage, String kafkaTopic) throws ExecutionException, InterruptedException, JsonProcessingException {
        var kafkaRecord = kafkaMessageProducer.createKafkaRecord(kafkaTopic, rentalRecordsMessage);
        kafkaTemplate.send(kafkaRecord).get();
    }
}
