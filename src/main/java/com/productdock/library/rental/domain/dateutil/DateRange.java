package com.productdock.library.rental.domain.dateutil;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
public class DateRange {

    private final Date dateFrom;
    private final Date dateTo;

    public DateRange(long dateFrom, long dateTo) {
        this.dateFrom = new Date(dateFrom);
        this.dateTo = new Date(dateTo);
    }

    public boolean includesWeekend() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFrom);

        while (!calendar.getTime().after(dateTo)) {
            var dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (isWeekend(dayOfWeek)) {
                return true;
            }

            nextDay(calendar);
        }
        return false;
    }

    private void nextDay(Calendar calendar) {
        calendar.add(Calendar.DATE, 1);
    }

    private boolean isWeekend(int dayOfWeek) {
        return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
    }
}
