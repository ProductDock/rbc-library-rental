package com.productdock.library.rental.adapter.out.kafka.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productdock.library.rental.adapter.out.kafka.BookRentalStatusChanged;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaMessageProducer {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProducerRecord<String, String> createKafkaRecord(String topic, BookRentalStatusChanged bookRentalStatusChanged) throws JsonProcessingException {
        log.debug("Create kafka record on topic {} with message: {}", topic, bookRentalStatusChanged);
        var serialisedMessage = serialiseMessageToJson(bookRentalStatusChanged);
        return new ProducerRecord<>(topic, bookRentalStatusChanged.getBookId(), serialisedMessage);
    }

    private String serialiseMessageToJson(Object message) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(message);
    }
}
