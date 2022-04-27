package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.record.RentalRecordEntity;
import com.productdock.library.rental.record.RentalRecordEntityDeserializer;
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
    private RentalRecordEntityDeserializer rentalRecordEntityDeserializer;

    @KafkaListener(topics = "${spring.kafka.topic.rental-record-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        var recordEntity = rentalRecordEntityDeserializer.deserializeRecordEntity(consumerRecord);
        writeRecordToFile(recordEntity);
    }

    private void writeRecordToFile(RentalRecordEntity rentalRecordEntity) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("testRecord.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(rentalRecordEntity);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
