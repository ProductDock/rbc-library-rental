package com.productdock.library.rental.util;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

class DateRangeShould {

    private static final Long WEDNESDAY_DATE = 1655287200000L;
    private static final Long MONDAY_DATE = 1655114400000L;


    @Test
    void showThatWeekendIsNotIncluded() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(WEDNESDAY_DATE);
        calendar.add(Calendar.DATE, 4);
        DateRange dateRange = new DateRange(WEDNESDAY_DATE, calendar.getTimeInMillis());

        assertThat(dateRange.includesWeekend()).isTrue();
    }

    @Test
    void showThatWeekendIsIncluded() {
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(MONDAY_DATE);
        calendar.add(Calendar.DATE, 4);
        DateRange dateRange = new DateRange(MONDAY_DATE, calendar.getTimeInMillis());

        assertThat(dateRange.includesWeekend()).isFalse();
    }
}
