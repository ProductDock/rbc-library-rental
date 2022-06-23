package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.CancelExpiredReservationsUseCase;
import com.productdock.library.rental.application.port.out.persistence.RentalRecordPersistenceOutPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
public class CancelExpiredReservationsService implements CancelExpiredReservationsUseCase {

    private RentalRecordPersistenceOutPort rentalRecordRepository;
    private ReservationExpirationPolicy reservationExpirationPolicy;

    @Override
    public void cancelExpiredReservations() {
        log.debug("Cancel reservations expired on date {}", new Date());
        var recordsWithReservations = rentalRecordRepository.findWithReservations();
        for (var record : recordsWithReservations) {
            record.removeExpiredReservations(reservationExpirationPolicy);
            rentalRecordRepository.save(record);
        }
    }
}
