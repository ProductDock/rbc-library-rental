package com.productdock.library.rental.util;

import com.productdock.library.rental.scheduled.WeekendPolicy;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

class FutureDateShould {

    private static final Date WEDNESDAY = new Date(1655287200000L);
    private static final Date SATURDAY = new Date(1655632800000L);
    private static final Date TUESDAY = new Date(1655805600000L);

    @Test
    void addDurationToDateWhenWorkdayPolicy() {
        var duration = new Duration(4, TimeUnit.DAYS);
        var futureDate = FutureDate.of(WEDNESDAY, WeekendPolicy.WORKDAYS).offset(duration);

        assertThat(futureDate).isEqualTo(TUESDAY);
    }

    @Test
    void addDurationToDateWhenWeekdayPolicy() {
        var duration = new Duration(4, TimeUnit.DAYS);
        var futureDate = FutureDate.of(WEDNESDAY, WeekendPolicy.WEEKDAYS).offset(duration);

        assertThat(futureDate).isEqualTo(SATURDAY);
    }
}
