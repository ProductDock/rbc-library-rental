package com.productdock.library.rental.util;

import com.productdock.library.rental.scheduled.WeekendPolicy;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
public class FutureDate {

    private Date currentDate;
    private WeekendPolicy weekendPolicy;
    private static final int WEEKEND_DAYS = 2;

    public static FutureDate of(Date date, WeekendPolicy weekendPolicy) {
        return new FutureDate(date, weekendPolicy);
    }

    public Date offset(Duration duration) {
        var calculatedTime = currentDate.getTime() + duration.getTimeUnit().toMillis(duration.getAmount());
        DateRange dateRange = new DateRange(currentDate.getTime(), calculatedTime);
        if (dateRange.includesWeekend() && weekendPolicy.equals(WeekendPolicy.WORKDAYS)) {
            calculatedTime += TimeUnit.DAYS.toMillis(WEEKEND_DAYS);
        }

        return new Date(calculatedTime);
    }
}
