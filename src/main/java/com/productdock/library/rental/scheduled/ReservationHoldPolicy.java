package com.productdock.library.rental.scheduled;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ReservationHoldPolicy {

    @Value("${reservations.auto-canceling.reservation-hold-policy.limit.value}")
    @With
    private int limit;

    @Value("${reservations.auto-canceling.reservation-hold-policy.limit.unit}")
    @With
    private String timeUnit;

    @Value("${reservations.auto-canceling.reservation-hold-policy.skip-weekends}")
    @With
    private boolean skippedWeekend;

    private TimeUnit getTimeUnit() {
        return TimeUnit.valueOf(timeUnit.toUpperCase());
    }

    public Long getLimitInMillis() {
        return getTimeUnit().toMillis(limit);
    }
}
