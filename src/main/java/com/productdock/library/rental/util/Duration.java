package com.productdock.library.rental.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Getter
public class Duration {

    private int amount;
    private TimeUnit timeUnit;
}
