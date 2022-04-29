package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.productdock.library.rental.kafka.RentalsProducer;
import com.productdock.library.rental.service.BookRentalRecordMapper;
import com.productdock.library.rental.service.RentalRecordsMessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.productdock.library.rental.data.provider.RentalRecordsMessageMother.defaultRentalRecordsMessageBuilder;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerShould {
    @InjectMocks
    private RentalsProducer rentalsProducer;

    @Mock
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Mock
    private RentalRecordsMessageMapper rentalRecordsMessageMapper;

    private String topic = "test-book-status";

    @Test
    void produceCorrectMessage() throws JsonProcessingException {
        var rentalRecordsMessage = defaultRentalRecordsMessageBuilder().rentalRecords(Collections.emptyList()).build();

        var producerRecord = rentalsProducer.createKafkaRecord(topic, rentalRecordsMessage);

        Gson gson = new Gson();
        String desiredValue = "{\"bookId\":\"" + rentalRecordsMessage.getBookId() +
                "\",\"rentalRecords\":" + gson.toJson(rentalRecordsMessage.getRentalRecords()) + "}";

        assertThat(producerRecord.value()).isEqualTo(desiredValue);
    }
}
