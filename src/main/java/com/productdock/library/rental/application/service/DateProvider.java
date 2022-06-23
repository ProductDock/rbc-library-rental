package com.productdock.library.rental.application.service;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateProvider {

    public Date now() {
        return new Date();
    }
}
