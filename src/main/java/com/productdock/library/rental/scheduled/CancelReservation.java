package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordService;
import com.productdock.library.rental.service.RentalRequestDto;
import com.productdock.library.rental.service.RentalStatus;
import com.productdock.library.rental.util.DateRange;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class CancelReservation {

    private RentalRecordService rentalRecordService;
    private DateProvider dateProvider;
    private ReservationHoldPolicy reservationHoldPolicy;

    private static final int WEEKEND_DAYS = 2;

    @Scheduled(cron = "${reservations.auto-canceling.scheduled}")
    public void schedule() {
        log.debug("Started scheduled task for canceling book reservations, date: {}", new Date());
        var rentalRecords = rentalRecordService.findAllReserved();
        for (var rentalRecord : rentalRecords) {
            cancelExpiredBookReservations(rentalRecord);
        }
    }

    private void cancelExpiredBookReservations(RentalRecordEntity rentalRecord) {
        var rentalRequestDto = new RentalRequestDto(rentalRecord.getBookId(), RentalStatus.CANCELED);

        for (var interaction : getReservedInteractions(rentalRecord)) {
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
        var executionTime = reservationTime.getTime() + reservationHoldPolicy.getTimeUnit().toMillis(reservationHoldPolicy.getLimit());
        DateRange dateRange = new DateRange(reservationTime.getTime(), executionTime);
        if (dateRange.includesWeekend() && reservationHoldPolicy.isSkippedWeekend()) {
            executionTime += reservationHoldPolicy.getTimeUnit().toMillis(WEEKEND_DAYS);
        }

        return new Date(executionTime);
    }
}
