package com.productdock.library.rental.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class RecordConsumer {

    @KafkaListener(topics = "book-status-topic")
    public synchronized void listen(ConsumerRecord<String, String> record,
                                    Acknowledgment acknowledgment) {

        System.out.print("Logged : " + record.value());
        acknowledgment.acknowledge();
    }
}
