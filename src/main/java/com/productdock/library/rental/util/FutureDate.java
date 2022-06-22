package com.productdock.library.rental.util;

import com.productdock.library.rental.scheduled.DaysOfTheWeek;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class FutureDate {

    private final Date date;
    private final DaysOfTheWeek daysOfTheWeek;
    private static final long WEEKEND = TimeUnit.DAYS.toMillis(2);

    public static FutureDate of(Date date, DaysOfTheWeek weekendPolicy) {
        return new FutureDate(date, weekendPolicy);
    }

    public Date offset(Duration duration) {
        var futureTime = date.getTime() + duration.getTime();
        DateRange dateRange = new DateRange(date.getTime(), futureTime);
        if (dateRange.includesWeekend() && daysOfTheWeek.equals(DaysOfTheWeek.WORKDAYS)) {
            return new Date(futureTime + WEEKEND);
        }

        return new Date(futureTime);
    }
}
