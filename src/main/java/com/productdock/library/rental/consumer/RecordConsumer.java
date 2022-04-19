package com.productdock.library.rental.consumer;

import com.productdock.library.rental.record.RecordEntityDeserializer;
import com.productdock.library.rental.record.RecordService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public record RecordConsumer(RecordService recordService, RecordEntityDeserializer recordEntityDeserializer) {

    @KafkaListener(topics = "${spring.kafka.topic.rental-record-warning-topic}")
    public synchronized void listen(ConsumerRecord<String, String> record) {
        var recordEntity = recordEntityDeserializer.deserializeRecordEntity(record);
        System.out.println(recordEntity.toString());
        recordService.saveRecordEntity(recordEntity);
    }
}
