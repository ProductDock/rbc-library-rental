//package com.productdock.library.rental.kafka;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.google.gson.Gson;
//import com.productdock.library.rental.ca.adapter.out.kafka.KafkaMessageProducer;
//import com.productdock.library.rental.ca.application.service.BookRentalRecordMapper;
//import com.productdock.library.rental.ca.domain.RentalRecordsMessageMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collections;
//
//import static com.productdock.library.rental.data.provider.RentalRecordsMessageMother.defaultRentalRecordsMessageBuilder;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(MockitoExtension.class)
//class KafkaProducerShould {
//    @InjectMocks
//    private KafkaMessageProducer kafkaMessageProducer;
//
//    @Mock
//    private BookRentalRecordMapper bookRentalRecordMapper;
//
//    @Mock
//    private RentalRecordsMessageMapper rentalRecordsMessageMapper;
//
//    private String topic = "test-book-status";
//
//    @Test
//    void produceMessage() throws JsonProcessingException {
//        var rentalRecordsMessage = defaultRentalRecordsMessageBuilder().rentalRecords(Collections.emptyList()).build();
//
//        var producerRecord = kafkaMessageProducer.createKafkaRecord(topic, rentalRecordsMessage);
//
//        Gson gson = new Gson();
//        String desiredValue = "{\"bookId\":\"" + rentalRecordsMessage.getBookId() +
//                "\",\"rentalRecords\":" + gson.toJson(rentalRecordsMessage.getRentalRecords()) + "}";
//
//        assertThat(producerRecord.value()).isEqualTo(desiredValue);
//    }
//}
