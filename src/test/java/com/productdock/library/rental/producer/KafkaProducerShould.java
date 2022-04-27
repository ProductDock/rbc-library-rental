package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.productdock.library.rental.record.RentalRecordEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerShould {
    @InjectMocks
    private RecordProducer recordProducer;

    private String topic = "test-rental-record-topic";

    @Test
    void produceCorrectMessage() throws JsonProcessingException {
        var rentalRecordEntity = defaultRentalRecordEntityBuilder().rents(Collections.emptyList()).reservations(Collections.emptyList()).build();
        var producerRecord = recordProducer.createKafkaRecord(topic, rentalRecordEntity);
        Gson gson = new Gson();
        String desiredValue = "{\"bookId\":\"" + rentalRecordEntity.getBookId() + "\",\"reservations\":" + gson.toJson(rentalRecordEntity.getReservations()) +
                ",\"rents\":" + gson.toJson(rentalRecordEntity.getRents()) + "}";

        assertThat(producerRecord.value()).isEqualTo(desiredValue);
    }

    @Test
    void throwExceptionWhenProducingBadEntity() {
        RentalRecordEntity rentalRecordEntity = mock(RentalRecordEntity.class);
        assertThrows(JsonProcessingException.class, () -> {
            var producerRecord = recordProducer.createKafkaRecord(topic, rentalRecordEntity);
        });
    }
}
