package com.productdock.library.rental.scheduled;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateProvider {

    public Date now() {
        return new Date();
    }
}
