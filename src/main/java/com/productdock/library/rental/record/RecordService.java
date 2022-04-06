package com.productdock.library.rental.record;

import org.springframework.stereotype.Service;

@Service
public record RecordService(RecordMapper recordMapper) {
    public void sendRecord() {
        var record = new RecordEntity(1L, "RENTED");
    }
}
