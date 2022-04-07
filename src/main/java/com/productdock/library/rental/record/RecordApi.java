package com.productdock.library.rental.record;

import com.productdock.library.rental.producer.Publisher;
import com.productdock.library.rental.producer.messages.Notification;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/record")
public record RecordApi(RecordService recordService) {
    @PostMapping
    public void sendRecord(@RequestBody RecordDTO recordDTO) {
        recordService.create(recordDTO);
    }
}
