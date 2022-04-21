package com.productdock.library.rental.record;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rental/record")
public record RecordApi(RecordService recordService) {
    @PostMapping
    public void sendRecord(@RequestBody RecordDTO recordDTO, @RequestHeader("Authorization") String authToken) {
        recordService.create(recordDTO, authToken);
    }
}
