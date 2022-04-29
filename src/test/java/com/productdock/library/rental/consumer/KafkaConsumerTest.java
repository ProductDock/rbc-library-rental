package com.productdock.library.rental.consumer;


import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.data.provider.KafkaTestProducer;
import com.productdock.library.rental.kafka.FailedRequestDeserializer;
import com.productdock.library.rental.service.FailedRequest;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordRepository;
import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
public class KafkaConsumerTest extends KafkaTestBase {

    @Autowired
    private KafkaTestProducer producer;

    @Autowired
    private RentalRecordRepository rentalRecordRepository;

    @Autowired
    private FailedRequestDeserializer failedRequestDeserializer;

    @Value("${spring.kafka.topic.bad-rental-request}")
    private String topic;

    private String userEmail = "default@gmail.com";
    private RentalStatus requestStatus = RentalStatus.RENTED;
    private String bookId = "1";

    @BeforeEach
    final void before() {
        rentalRecordRepository.deleteAll();
    }

    @Test
    void shouldSaveBookIndex_whenMessageReceived() {
        givenOneRentalRecord();
        var failedRequest = new FailedRequest(bookId, userEmail, requestStatus);
        producer.send(topic, failedRequest);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(() -> rentalRecordRepository.findById(bookId).get().getInteractions().isEmpty());
        System.out.println(rentalRecordRepository.findById("1").get());
        assertThat(rentalRecordRepository.findById("1").get().getInteractions().size()).isZero();
    }

    private void givenOneRentalRecord() {
        var rentalRecord = new RentalRecordEntity(bookId, new LinkedList<>
                (Arrays.asList(new BookInteraction(userEmail, new Date(), requestStatus))));
        rentalRecordRepository.save(rentalRecord);
    }
}
