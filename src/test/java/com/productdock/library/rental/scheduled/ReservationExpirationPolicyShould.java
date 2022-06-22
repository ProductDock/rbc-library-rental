package com.productdock.library.rental.scheduled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ReservationExpirationPolicyShould {

    private static final DateProvider dateProvider = mock(DateProvider.class);

    private static final int TIME_LIMIT = 4;

    private static final Date DATE_13_06_2022 = new Date(1655107200000L);
    private static final Date DATE_15_06_2022 = new Date(1655287200000L);
    private static final Date DATE_17_06_2022 = new Date(1655460000000L);
    private static final Date DATE_20_06_2022 = new Date(1655719200000L);
    private static final Date DATE_21_06_2022 = new Date(1655805600000L);

    @ParameterizedTest
    @MethodSource("testArguments")
    void checkIfReservationIsExpired(
            Date currentDate,
            Date reservationDate,
            DaysOfTheWeek daysOfTheWeekPolicy,
            boolean expired) {
        given(dateProvider.now()).willReturn(currentDate);
        var policy = ReservationExpirationPolicy.builder()
                .timeLimit(TIME_LIMIT)
                .timeUnit(TimeUnit.DAYS)
                .daysOfTheWeek(daysOfTheWeekPolicy)
                .dateProvider(dateProvider).build();

        var isExpired = policy.isExpired(reservationDate);

        assertThat(isExpired).isEqualTo(expired);
    }

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(DATE_15_06_2022, DATE_13_06_2022, DaysOfTheWeek.ALL_DAYS, false),
                Arguments.of(DATE_17_06_2022, DATE_13_06_2022, DaysOfTheWeek.ALL_DAYS, true),
                Arguments.of(DATE_20_06_2022, DATE_15_06_2022, DaysOfTheWeek.WORKDAYS, false),
                Arguments.of(DATE_21_06_2022, DATE_15_06_2022, DaysOfTheWeek.WORKDAYS, true)
        );
    }
}
