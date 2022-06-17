package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordService;
import com.productdock.library.rental.service.RentalRequestDto;
import com.productdock.library.rental.service.RentalStatus;
import com.productdock.library.rental.util.DateRange;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class CancelReservation {

    @NonNull
    private RentalRecordService rentalRecordService;

    @NonNull
    private DateProvider dateProvider;

    @Value("${scheduled.task.delay}")
    private int delay;

    @Value("${scheduled.task.time-to-skip-weekend}")
    private int timeToSkipWeekend;

    @Scheduled(cron = "${scheduled.cron.expression}")
    public void schedule() {
        log.debug("Started scheduled task for canceling book reservations, date: {}", new Date());
        var rentalRecords = rentalRecordService.findAllReserved();
        for (var rentalRecord : rentalRecords) {
            cancelExpiredBookReservations(rentalRecord);
        }
    }

    private void cancelExpiredBookReservations(RentalRecordEntity rentalRecord) {
        var rentalRequestDto = new RentalRequestDto(rentalRecord.getBookId(), RentalStatus.CANCELED);

        for (var interaction :  getReservedInteractions(rentalRecord)) {
            log.debug("Found reserved book interaction: {} for book with id: {}", interaction, rentalRecord.getBookId());
            if (timeToCancelReservation(interaction.getDate())) {
                rentalRecordService.create(rentalRequestDto, interaction.getUserEmail());
            }
        }
    }

    private List<BookInteraction> getReservedInteractions(RentalRecordEntity rentalRecord) {
        return rentalRecord.getInteractions().stream().filter(i -> i.getStatus().equals(RentalStatus.RESERVED)).toList();
    }

    private boolean timeToCancelReservation(Date reservationTime) {
        var currentTime = dateProvider.now();
        return !getExecutionTime(reservationTime).after(currentTime);
    }

    private Date getExecutionTime(Date reservationTime) {
        var executionTime = reservationTime.getTime() + TimeUnit.SECONDS.toMillis(delay);
        DateRange dateRange = new DateRange(reservationTime.getTime(), executionTime);
        if (dateRange.includesWeekend()) {
            executionTime += TimeUnit.SECONDS.toMillis(timeToSkipWeekend);
        }

        return new Date(executionTime);
    }
}
