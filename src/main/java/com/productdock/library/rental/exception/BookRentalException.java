package com.productdock.library.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BookRentalException extends RuntimeException {

    public BookRentalException(String message) {
        super(message);
    }
}
