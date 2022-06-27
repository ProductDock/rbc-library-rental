package com.productdock.library.rental.domain.dateutil;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class FutureDateShould {

    private static final Date WEDNESDAY = new Date(1655287200000L);
    private static final Date SATURDAY = new Date(1655632800000L);
    private static final Date TUESDAY = new Date(1655805600000L);

    @ParameterizedTest
    @MethodSource("testArguments")
    void addDurationToDate(DaysOfTheWeek daysOfTheWeekPolicy, Date date, Date expectedFutureDate) {
        var duration = new Duration(4, TimeUnit.DAYS);
        var futureDate = FutureDate.of(date, daysOfTheWeekPolicy).offset(duration);

        assertThat(futureDate).isEqualTo(expectedFutureDate);
    }

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(DaysOfTheWeek.WORKDAYS, WEDNESDAY, TUESDAY),
                Arguments.of(DaysOfTheWeek.ALL_DAYS, WEDNESDAY, SATURDAY)
                );
    }
}
