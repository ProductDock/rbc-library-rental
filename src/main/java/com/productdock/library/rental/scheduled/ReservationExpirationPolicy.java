package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.util.Duration;
import com.productdock.library.rental.util.FutureDate;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ReservationExpirationPolicy {

    @Value("${reservations.auto-canceling.reservation-expiration-policy.limit.value}")
    private int timeLimit;

    @Value("${reservations.auto-canceling.reservation-expiration-policy.limit.unit}")
    private TimeUnit timeUnit;

    @Value("${reservations.auto-canceling.reservation-expiration-policy.week-policy}")
    private DaysOfTheWeek daysOfTheWeek;

    private DateProvider dateProvider;

    @Autowired
    public ReservationExpirationPolicy(DateProvider dateProvider) {
        this.dateProvider = dateProvider;
    }

    public boolean isExpired(Date date) {
        var currentTime = dateProvider.now();
        var maxReservationTime = new Duration(timeLimit, timeUnit);
        var expirationTime = FutureDate.of(date, daysOfTheWeek).offset(maxReservationTime);
        return !expirationTime.after(currentTime);
    }

}
