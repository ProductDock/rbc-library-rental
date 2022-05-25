package com.productdock.library.rental.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.application.port.out.kafka.SendRentalRecordOutPort;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.RentalRecordsMessage;
import com.productdock.library.rental.domain.RentalRecordsMessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@RequiredArgsConstructor
@Component
class RentalRecordPublisher implements SendRentalRecordOutPort {

    private final RentalRecordsMessageMapper rentalRecordsMessageMapper;
    private final KafkaPublisher publisher;

    @Value("${spring.kafka.topic.book-status}")
    private String kafkaTopic;

    @Override
    public void sendMessage(BookRentalRecord rentalRecord) throws ExecutionException, InterruptedException, JsonProcessingException {
        RentalRecordsMessage message = rentalRecordsMessageMapper.toMessage(rentalRecord);
        publisher.sendMessage(message, kafkaTopic);
    }

}
