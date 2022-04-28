package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.kafka.RecordProducer;
import com.productdock.library.rental.service.BookRentalRecordMapper;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordsMessage;
import com.productdock.library.rental.service.RentalRecordsMessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static com.productdock.library.rental.data.provider.RentalRecordsMessageMother.defaultRentalRecordsMessageBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerShould {
    @InjectMocks
    private RecordProducer recordProducer;

    @Mock
    private BookRentalRecordMapper bookRentalRecordMapper;

    @Mock
    private RentalRecordsMessageMapper rentalRecordsMessageMapper;

    private String topic = "test-rental-record-topic";

    @Test
    void produceCorrectMessage() throws JsonProcessingException {
        var rentalRecordsMessage = defaultRentalRecordsMessageBuilder().rentalRecords(Collections.emptyList()).build();

        var producerRecord = recordProducer.createKafkaRecord(topic, rentalRecordsMessage);

        Gson gson = new Gson();
        String desiredValue = "{\"bookId\":\"" + rentalRecordsMessage.getBookId() +
                "\",\"rentalRecords\":" + gson.toJson(rentalRecordsMessage.getRentalRecords()) + "}";

        assertThat(producerRecord.value()).isEqualTo(desiredValue);
    }
}
