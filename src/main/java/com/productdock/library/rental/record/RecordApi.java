package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/record")
public record RecordApi(RecordService recordService, Publisher publisher) {
    @PostMapping
    public void sendRecord() {
        publisher.sendMessage(new RecordEntity(1L, "RENTED"));
    }
}
