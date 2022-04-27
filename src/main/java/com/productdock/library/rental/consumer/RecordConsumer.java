package com.productdock.library.rental.consumer;

import com.productdock.library.rental.record.RentalRecordEntityDeserializer;
import com.productdock.library.rental.record.RentalRecordService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public record RecordConsumer(RentalRecordService rentalRecordService,
                             RentalRecordEntityDeserializer rentalRecordEntityDeserializer) {

    @KafkaListener(topics = "${spring.kafka.topic.rental-record-warning-topic}")
    public synchronized void listen(ConsumerRecord<String, String> record) {
        var recordEntity = rentalRecordEntityDeserializer.deserializeRecordEntity(record);
        rentalRecordService.saveRecordEntity(recordEntity);
    }
}
