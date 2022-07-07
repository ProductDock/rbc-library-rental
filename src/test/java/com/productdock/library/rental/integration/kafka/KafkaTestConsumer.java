package com.productdock.library.rental.integration.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.adapter.out.kafka.BookRentalStatusChanged;
import com.productdock.library.rental.kafka.RentalRecordsMessageDeserializer;
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
    private RentalRecordsMessageDeserializer rentalRecordsMessageDeserializer;


    @KafkaListener(topics = "${spring.kafka.topic.publishing}")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        LOGGER.info("received payload='{}'", consumerRecord.toString());
        var rentalRecordsMessage = rentalRecordsMessageDeserializer.deserializeRentalRecordsMessage(consumerRecord);
        writeRecordToFile(rentalRecordsMessage);
    }

    private void writeRecordToFile(BookRentalStatusChanged bookRentalStatusChanged) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("testRecord.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(bookRentalStatusChanged);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
