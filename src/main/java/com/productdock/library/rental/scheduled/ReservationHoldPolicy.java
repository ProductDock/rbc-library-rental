package com.productdock.library.rental.scheduled;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Getter
@NoArgsConstructor
public class ReservationHoldPolicy {

    @Value("${reservations.auto-canceling.reservation-hold-policy.limit.value}")
    private int limit;

    @Value("${reservations.auto-canceling.reservation-hold-policy.limit.unit}")
    private String timeUnit;

    @Value("${reservations.auto-canceling.reservation-hold-policy.skip-weekends}")
    private boolean skippedWeekend;

    public TimeUnit getTimeUnit() {
        return TimeUnit.valueOf(timeUnit.toUpperCase());
    }
}
