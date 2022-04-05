package com.productdock.library.rental.record;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/record")
public record RecordApi(RecordService recordService) {
    @GetMapping
    public RecordEntity getRecord() {
        return recordService.getRecord();
    }
}
