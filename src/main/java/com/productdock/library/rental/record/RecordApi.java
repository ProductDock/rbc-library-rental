package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import com.productdock.library.rental.producer.messages.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/record")
public record RecordApi(Publisher publisher) {
    @PostMapping
    public void sendRecord(@RequestBody Notification notification) {
        publisher.sendMessage(new RecordEntity(1L, notification));
    }
}
