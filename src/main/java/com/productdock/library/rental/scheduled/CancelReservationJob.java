package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.service.RentalRecordService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
public class CancelReservationJob {

    private RentalRecordService rentalRecordService;
    private ReservationExpirationPolicy reservationExpirationPolicy;

    @Scheduled(cron = "${reservations.auto-canceling.scheduled}")
    public void cancelReservation() {
        log.debug("Started scheduled task for canceling book reservations, date: {}", new Date());
        var rentalRecords = rentalRecordService.findWithReservations();
        for (var rentalRecord : rentalRecords) {
            rentalRecord.removeExpiredReservations(reservationExpirationPolicy);
            rentalRecordService.saveRentalRecord(rentalRecord);
        }
    }
}
