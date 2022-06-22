package com.productdock.library.rental.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class Duration {

    private int value;
    private TimeUnit timeUnit;

    public long getTime() {
        return timeUnit.toMillis(value);
    }
}
