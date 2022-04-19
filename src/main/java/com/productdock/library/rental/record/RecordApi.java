package com.productdock.library.rental.record;

import com.productdock.library.rental.exception.NotFoundException;
import com.productdock.library.rental.producer.Publisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("api/record")
public record RecordApi(RecordService recordService/*, JwtToken*/) {
    @PostMapping
    public void sendRecord(@RequestBody RecordDTO recordDTO/*, JwtToken token*/){
        recordService.create(recordDTO);
    }
}
