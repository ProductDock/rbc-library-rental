package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.messaging.BookRentalsMessagingOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalEventPersistenceOutPort;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentalEvent;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.ReservationExpirationPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CancelExpiredReservationServiceShould {

    @InjectMocks
    private CancelExpiredReservationsService service;

    @Mock
    private BookRentalsPersistenceOutPort bookRentalsRepository;

    @Mock
    private BookRentalEventPersistenceOutPort bookRentalEventRepository;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

    @Mock
    private BookRentalsMessagingOutPort bookRentalsPublisher;

    private static final BookRentals BOOK_WITH_EXPIRED_RESERVATION = mock(BookRentals.class);
    private static final List<BookRentals.BookCopyRentalState> BOOK_EXPIRED_RENTAL_STATES = of(mock(BookRentals.BookCopyRentalState.class));

    @Test
    void cancelExpiredReservations() throws Exception {
        given(bookRentalsRepository.findWithReservations()).willReturn(of(BOOK_WITH_EXPIRED_RESERVATION));
        given(BOOK_WITH_EXPIRED_RESERVATION.findExpiredReservations(reservationExpirationPolicy)).willReturn(BOOK_EXPIRED_RENTAL_STATES);

        service.cancelExpiredReservations();

        verify(BOOK_WITH_EXPIRED_RESERVATION).removeExpiredReservations(reservationExpirationPolicy);
        verify(bookRentalEventRepository).save(any(BookRentalEvent.class));
        verify(bookRentalsRepository).save(BOOK_WITH_EXPIRED_RESERVATION);
        verify(bookRentalsPublisher).sendMessage(BOOK_WITH_EXPIRED_RESERVATION);
    }

}
