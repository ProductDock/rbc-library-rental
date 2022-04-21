package com.productdock.library.rental.consumer;


import com.productdock.library.rental.data.provider.KafkaTestProducer;
import com.productdock.library.rental.data.provider.KafkaTestBase;
import com.productdock.library.rental.record.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;

import static com.productdock.library.rental.data.provider.RecordEntityMother.defaultRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest
public class KafkaConsumerTest extends KafkaTestBase {

    @Autowired
    private KafkaTestProducer producer;

    @Autowired
    private RecordRepository recordRepository;

    @Value("${spring.kafka.topic.rental-record-warning-topic}")
    private String topic;

    @BeforeEach
    final void before() {
        recordRepository.deleteAll();
    }

    @Test
    void shouldSaveBookIndex_whenMessageReceived() {
        var recordEntity = defaultRecordEntityBuilder().build();
        producer.send(topic, recordEntity);
        await()
                .atMost(Duration.ofSeconds(20))
                .until(() -> recordRepository.findById("1").isPresent());
        assertThat(recordRepository.findById("1").get().getRents()).isNotNull();
    }
}
