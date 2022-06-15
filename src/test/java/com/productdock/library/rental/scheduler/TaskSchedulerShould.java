package com.productdock.library.rental.scheduler;

import com.productdock.library.rental.util.DateRange;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import org.mockito.MockedConstruction.Context;

@ExtendWith(MockitoExtension.class)
class TaskSchedulerShould {

    private static final int DAY_IN_SECONDS = 86400;
    private static final int fourDaysDelay = 345600;

    private static final Runnable scheduledTaskMock = mock(Runnable.class);
    private static final Integer defaultDelay = 3;

    private static final TimeUnit defaultTimeUnit = TimeUnit.SECONDS;
    private static final ScheduledExecutorService executorServiceMock = mock(ScheduledExecutorService.class);

    private TaskScheduler taskScheduler = new TaskScheduler();

    @Test
    void scheduleTask() {
        try (MockedStatic<Executors> executors = mockStatic(Executors.class)) {
            executors.when(() -> Executors.newScheduledThreadPool(anyInt())).thenReturn(executorServiceMock);

            taskScheduler
                    .scheduledTask(scheduledTaskMock)
                    .schedulerRule(SchedulerRule.WEEKDAYS)
                    .delay(defaultDelay)
                    .timeUnit(defaultTimeUnit).schedule();
        }

        verify(executorServiceMock).schedule(scheduledTaskMock, defaultDelay, defaultTimeUnit);
    }


    @Test
    void scheduleTaskWhenWeekendIncludedAndWorkDaysRuleApplied() {

        try (MockedConstruction<DateRange> mockedDateRange =
                     mockConstruction(DateRange.class, (dateRangeMock, context) -> prepareDateRange(dateRangeMock, true, context))) {

            try (MockedStatic<Executors> executors = mockStatic(Executors.class)) {
                executors.when(() -> Executors.newScheduledThreadPool(anyInt())).thenReturn(executorServiceMock);

                taskScheduler
                        .scheduledTask(scheduledTaskMock)
                        .schedulerRule(SchedulerRule.WORKDAYS)
                        .delay(fourDaysDelay)
                        .timeUnit(defaultTimeUnit).schedule();
            }
        }


        verify(executorServiceMock).schedule(scheduledTaskMock, fourDaysDelay + 2 * DAY_IN_SECONDS, defaultTimeUnit);
    }

    @Test
    void scheduleTaskWhenWeekendNotIncludedAndWorkDaysRuleApplied() {

        try (MockedConstruction<DateRange> mockedDateRange =
                     mockConstruction(DateRange.class, (dateRangeMock, context) -> prepareDateRange(dateRangeMock, false, context))) {

            try (MockedStatic<Executors> executors = Mockito.mockStatic(Executors.class)) {
                executors.when(() -> Executors.newScheduledThreadPool(anyInt())).thenReturn(executorServiceMock);

                taskScheduler
                        .scheduledTask(scheduledTaskMock)
                        .schedulerRule(SchedulerRule.WORKDAYS)
                        .delay(fourDaysDelay)
                        .timeUnit(defaultTimeUnit).schedule();
            }
        }


        verify(executorServiceMock).schedule(scheduledTaskMock, fourDaysDelay, defaultTimeUnit);
    }

    private void prepareDateRange(DateRange dateRangeMock, boolean includesWeekend, Context context) {
        doReturn(includesWeekend).when(dateRangeMock).includesWeekend();
    }
}
