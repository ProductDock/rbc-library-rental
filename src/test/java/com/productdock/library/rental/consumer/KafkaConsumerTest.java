package com.productdock.library.rental.consumer;


import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.data.provider.KafkaTestProducer;
import com.productdock.library.rental.record.RentalRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
public class KafkaConsumerTest extends KafkaTestBase {

    @Autowired
    private KafkaTestProducer producer;

    @Autowired
    private RentalRecordRepository rentalRecordRepository;

    @Value("${spring.kafka.topic.rental-record-warning-topic}")
    private String topic;

    @BeforeEach
    final void before() {
        rentalRecordRepository.deleteAll();
    }

    @Test
    void shouldSaveBookIndex_whenMessageReceived() {
        var rentalRecordEntity = defaultRentalRecordEntityBuilder().build();
        producer.send(topic, rentalRecordEntity);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(() -> rentalRecordRepository.findById("1").isPresent());
        assertThat(rentalRecordRepository.findById("1").get().getRents()).isNotNull();
    }
}
