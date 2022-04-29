package com.productdock.library.rental.kafka;

import com.productdock.library.rental.service.FailedRequest;
import com.productdock.library.rental.service.RentalRecordService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public record FailedRequestConsumer(RentalRecordService rentalRecordService,
                                    FailedRequestDeserializer failedRequestDeserializer) {

    @KafkaListener(topics = "${spring.kafka.topic.rental-record-warning-topic}")
    public synchronized void listen(ConsumerRecord<String, String> record) {
        FailedRequest failedRequest = failedRequestDeserializer.deserializeFailedRequest(record);
        rentalRecordService.processFailedRequest(failedRequest);
    }
}
