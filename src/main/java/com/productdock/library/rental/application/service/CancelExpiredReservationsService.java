package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.CancelExpiredReservationsUseCase;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.ReservationExpirationPolicy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
class CancelExpiredReservationsService implements CancelExpiredReservationsUseCase {

    private BookRentalsPersistenceOutPort bookRentalsRepository;
    private ReservationExpirationPolicy reservationExpirationPolicy;

    @Override
    public void cancelExpiredReservations() {
        log.debug("Cancel reservations expired on date {}", new Date());
        var booksWithReservedCopies = bookRentalsRepository.findWithReservations();
        for (var book : booksWithReservedCopies) {
            book.removeExpiredReservations(reservationExpirationPolicy);
            bookRentalsRepository.save(book);
        }
    }
}
