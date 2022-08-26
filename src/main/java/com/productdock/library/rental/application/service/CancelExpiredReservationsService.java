package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.CancelExpiredReservationsUseCase;
import com.productdock.library.rental.application.port.out.persistence.BookRentalEventPersistenceOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

@Slf4j
@AllArgsConstructor
@Component
class CancelExpiredReservationsService implements CancelExpiredReservationsUseCase {

    private BookRentalsPersistenceOutPort bookRentalsRepository;
    private BookRentalEventPersistenceOutPort bookRentalEventRepository;
    private ReservationExpirationPolicy reservationExpirationPolicy;

    @Override
    public void cancelExpiredReservations() {
        log.debug("Cancel reservations expired on date {}", new Date());
        var booksWithReservedCopies = bookRentalsRepository.findWithReservations();
        for (var book : booksWithReservedCopies) {
            var expiredReservations = book.findExpiredReservations(reservationExpirationPolicy);
            book.removeExpiredReservations(reservationExpirationPolicy);
            bookRentalsRepository.save(book);
            saveCancelReservationEvents(book.getBookId(), expiredReservations);
        }
    }

    private void saveCancelReservationEvents(String bookId, Collection<BookRentals.BookCopyRentalState> expiredReservations) {
        for (var reservation: expiredReservations) {
            var cancelBookReservationEvent = new BookRentalEvent(new RentalAction(bookId, reservation.getPatron(), RentalActionType.SCHEDULED_CANCEL_RESERVATION));
            bookRentalEventRepository.save(cancelBookReservationEvent);
        }
    }
}
