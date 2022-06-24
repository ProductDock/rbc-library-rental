package com.productdock.library.rental.application.port.out.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.productdock.library.rental.domain.BookRentals;

import java.util.concurrent.ExecutionException;

public interface BookRentalsMessagingOutPort {

    void sendMessage(BookRentals bookRentals) throws ExecutionException, InterruptedException, JsonProcessingException;

}
