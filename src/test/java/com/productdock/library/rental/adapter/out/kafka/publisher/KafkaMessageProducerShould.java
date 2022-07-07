package com.productdock.library.rental.adapter.out.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.productdock.library.rental.data.provider.out.kafka.RentalRecordsMessageMother.defaultRentalRecordsMessageBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class KafkaMessageProducerShould {

    @InjectMocks
    private KafkaMessageProducer kafkaMessageProducer;

    @Test
    void produceMessage() throws JsonProcessingException {
        var rentalRecordsMessage = defaultRentalRecordsMessageBuilder().rentalRecords(Collections.emptyList()).build();

        var producerRecord = kafkaMessageProducer.createKafkaRecord("any_topic", rentalRecordsMessage);

        Gson gson = new Gson();
        String desiredValue = "{\"bookId\":\"" + rentalRecordsMessage.getBookId() +
                "\",\"rentalRecords\":" + gson.toJson(rentalRecordsMessage.getRentalRecords()) + "}";

        assertThat(producerRecord.value()).isEqualTo(desiredValue);
    }
}
