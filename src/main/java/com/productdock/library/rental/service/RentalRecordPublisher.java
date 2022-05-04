package com.productdock.library.rental.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.kafka.KafkaPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class RentalRecordPublisher {

    private final RentalRecordsMessageMapper rentalRecordsMessageMapper;
    private final KafkaPublisher publisher;

    public RentalRecordPublisher(RentalRecordsMessageMapper rentalRecordsMessageMapper, KafkaPublisher publisher) {
        this.rentalRecordsMessageMapper = rentalRecordsMessageMapper;
        this.publisher = publisher;
    }

    @Value("${spring.kafka.topic.book-status}")
    private String kafkaTopic;

    public void sendMessage(BookRentalRecord rentalRecord) throws ExecutionException, InterruptedException, JsonProcessingException {
        RentalRecordsMessage message = rentalRecordsMessageMapper.toMessage(rentalRecord);
        publisher.sendMessage(message, kafkaTopic);
    }

}
