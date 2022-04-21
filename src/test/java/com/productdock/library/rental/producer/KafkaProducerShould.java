package com.productdock.library.rental.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.productdock.library.rental.producer.RecordProducer;
import com.productdock.library.rental.record.RecordEntity;
import org.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import static com.productdock.library.rental.data.provider.RecordEntityMother.defaultRecordEntity;
import static com.productdock.library.rental.data.provider.RecordEntityMother.defaultRecordEntityBuilder;
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
        var recordEntity = defaultRecordEntityBuilder().rents(Collections.emptyList()).reservations(Collections.emptyList()).build();
        var producerRecord = recordProducer.createKafkaRecord(topic, recordEntity);
        Gson gson = new Gson();
        String desiredValue = "{\"bookId\":\"" + recordEntity.getBookId() + "\",\"reservations\":" + gson.toJson(recordEntity.getReservations()) +
                ",\"rents\":" + gson.toJson(recordEntity.getRents()) + "}";

        assertThat(producerRecord.value()).isEqualTo(desiredValue);
    }

    @Test
    void throwExceptionWhenProducingBadEntity() {
        RecordEntity recordEntity = mock(RecordEntity.class);
        assertThrows(JsonProcessingException.class, () -> {
                var producerRecord = recordProducer.createKafkaRecord(topic, recordEntity);
        });
    }
}
