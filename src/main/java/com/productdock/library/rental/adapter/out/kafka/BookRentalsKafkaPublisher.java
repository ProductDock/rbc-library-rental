package com.productdock.library.rental.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.adapter.out.kafka.mapper.BookRentalsMessageMapper;
import com.productdock.library.rental.adapter.out.kafka.publisher.KafkaPublisher;
import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.domain.BookRentals;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;

@Component
public class BookRentalsKafkaPublisher implements BookRentalsMessagingOutPort {

    private final BookRentalsMessageMapper bookRentalsMessageMapper;
    private final KafkaPublisher publisher;

    public BookRentalsKafkaPublisher(BookRentalsMessageMapper bookRentalsMessageMapper, KafkaPublisher publisher) {
        this.bookRentalsMessageMapper = bookRentalsMessageMapper;
        this.publisher = publisher;
    }

    @Value("${spring.kafka.topic.book-status}")
    private String kafkaTopic;

    @Override
    public void sendMessage(BookRentals bookRentals) throws ExecutionException, InterruptedException, JsonProcessingException {
        BookRentalsMessage message = bookRentalsMessageMapper.toMessage(bookRentals);
        publisher.sendMessage(message, kafkaTopic);
    }

}
