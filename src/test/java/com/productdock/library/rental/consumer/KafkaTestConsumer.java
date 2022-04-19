package com.productdock.library.rental.consumer;

import com.productdock.library.rental.record.RecordEntity;
import com.productdock.library.rental.record.RecordEntityDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


@Component
public class KafkaTestConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTestConsumer.class);

    @Autowired
    private RecordEntityDeserializer rentalRecordDeserializer;

    @KafkaListener(topics = "${spring.kafka.topic.rental-record-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        var rentalRecord = rentalRecordDeserializer.deserializeRecordEntity(consumerRecord);
        writeRecordToFile(rentalRecord);
    }

    private void writeRecordToFile(RecordEntity recordEntity) {
        try {
            FileOutputStream  fileOutputStream = new FileOutputStream("testRecord.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(recordEntity);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
