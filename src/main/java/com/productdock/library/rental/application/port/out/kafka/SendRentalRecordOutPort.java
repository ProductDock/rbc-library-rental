package com.productdock.library.rental.application.port.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.concurrent.ExecutionException;

public interface SendRentalRecordOutPort {

    void sendMessage(BookRentalRecord rentalRecord) throws ExecutionException, InterruptedException, JsonProcessingException;
}
