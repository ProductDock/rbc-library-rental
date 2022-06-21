package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.util.Duration;
import com.productdock.library.rental.util.FutureDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class ReservationExpirationPolicy {

    @Value("${reservations.auto-canceling.reservation-expiration-policy.limit.value}")
    @With
    private int limit;

    @Value("${reservations.auto-canceling.reservation-expiration-policy.limit.unit}")
    @With
    private TimeUnit timeUnit;

    @Value("${reservations.auto-canceling.reservation-expiration-policy.weekend-policy}")
    @With
    private WeekendPolicy weekendPolicy;

    @Autowired
    private DateProvider dateProvider;

    public boolean isExpired(Date date) {
        var currentTime = dateProvider.now();
        var maxReservationTime = new Duration(limit, timeUnit);
        var expirationTime = FutureDate.of(date, weekendPolicy).offset(maxReservationTime);
        return !expirationTime.after(currentTime);
    }

}
