package com.productdock.library.rental.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ReservationExpirationPolicyShould {

    private static final DateProvider dateProvider = mock(DateProvider.class);

    private static final int limit = 4;

    private static final Date DATE_13_06_2022 = new Date(1655107200000L);
    private static final Date DATE_15_06_2022 = new Date(1655287200000L);
    private static final Date DATE_17_06_2022 = new Date(1655460000000L);
    private static final Date DATE_20_06_2022 = new Date(1655719200000L);
    private static final Date DATE_21_06_2022 = new Date(1655805600000L);

    @Test
    void checkIfReservationIsExpiredWhenWeekdaysPolicyAndCurrentTimeBeforeExpiration() {
        given(dateProvider.now()).willReturn(DATE_15_06_2022);
        var policy = ReservationExpirationPolicy.builder()
                .limit(limit)
                .weekendPolicy(WeekendPolicy.WEEKDAYS)
                .timeUnit(TimeUnit.DAYS)
                .dateProvider(dateProvider).build();

        var isExpired = policy.isExpired(DATE_13_06_2022);

        assertThat(isExpired).isFalse();
    }

    @Test
    void checkIfReservationIsExpiredWhenWeekdaysPolicyAndCurrentTimeAfterExpiration() {
        given(dateProvider.now()).willReturn(DATE_17_06_2022);
        var policy = ReservationExpirationPolicy.builder()
                .limit(limit)
                .weekendPolicy(WeekendPolicy.WEEKDAYS)
                .timeUnit(TimeUnit.DAYS)
                .dateProvider(dateProvider).build();

        var isExpired = policy.isExpired(DATE_13_06_2022);

        assertThat(isExpired).isTrue();
    }


    @Test
    void checkIfReservationIsExpiredWhenWorkdaysPolicyAndCurrentTimeBeforeExpiration() {
        given(dateProvider.now()).willReturn(DATE_20_06_2022);
        var policy = ReservationExpirationPolicy.builder()
                .limit(limit)
                .weekendPolicy(WeekendPolicy.WORKDAYS)
                .timeUnit(TimeUnit.DAYS)
                .dateProvider(dateProvider).build();

        var isExpired = policy.isExpired(DATE_15_06_2022);

        assertThat(isExpired).isFalse();
    }

    @Test
    void checkIfReservationIsExpiredWhenWorkdaysPolicyAndCurrentTimeAfterExpiration() {
        given(dateProvider.now()).willReturn(DATE_21_06_2022);
        var policy = ReservationExpirationPolicy.builder()
                .limit(limit)
                .weekendPolicy(WeekendPolicy.WORKDAYS)
                .timeUnit(TimeUnit.DAYS)
                .dateProvider(dateProvider).build();

        var isExpired = policy.isExpired(DATE_15_06_2022);

        assertThat(isExpired).isTrue();
    }
}
