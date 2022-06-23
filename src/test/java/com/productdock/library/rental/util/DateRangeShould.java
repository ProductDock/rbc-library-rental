package com.productdock.library.rental.util;

import com.productdock.library.rental.domain.dateutil.DateRange;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DateRangeShould {

    private static final Long MONDAY = 1655114400000L;
    private static final Long WEDNESDAY = 1655287200000L;
    private static final Long SUNDAY = 1655625600000L;

    @ParameterizedTest
    @MethodSource("testArguments")
    void showIfWeekendIsInRange(long startTime, long endTime, boolean isIncluded) {
        DateRange dateRange = new DateRange(startTime, endTime);

        assertThat(dateRange.includesWeekend()).isEqualTo(isIncluded);
    }

    static Stream<Arguments> testArguments() {
        return Stream.of(
                Arguments.of(WEDNESDAY, SUNDAY, true),
                Arguments.of(MONDAY, WEDNESDAY, false)
        );
    }
}
