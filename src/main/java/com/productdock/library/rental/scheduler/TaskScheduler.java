package com.productdock.library.rental.scheduler;

import com.productdock.library.rental.util.DateRange;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@Getter @Setter
@Accessors(fluent = true)
public class TaskScheduler {

    private static final int DAY_IN_SECONDS = 86400;

    private SchedulerRule schedulerRule;
    private Runnable scheduledTask;
    private Integer delay;
    private TimeUnit timeUnit;

    public void schedule() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
        executor.schedule(scheduledTask, getScheduledTaskDelay(), timeUnit);
    }

    private int getScheduledTaskDelay() {
        if (schedulerRule.equals(SchedulerRule.WORKDAYS)) {
            return skipWeekend();
        }

        return delay;
    }

    private int skipWeekend() {
        var currentDate = new Date();
        DateRange dateRange = new DateRange(currentDate.getTime(), currentDate.getTime() + delay);
        if (dateRange.includesWeekend()) {
            return delay + 2 * DAY_IN_SECONDS;
        }

        return delay;
    }
}
