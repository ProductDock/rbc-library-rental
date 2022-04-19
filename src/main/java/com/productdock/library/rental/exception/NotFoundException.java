package com.productdock.library.rental.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason="User has already rented a book")
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }
}
