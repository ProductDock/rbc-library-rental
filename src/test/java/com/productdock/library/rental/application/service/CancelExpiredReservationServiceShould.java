package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.ReservationExpirationPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static java.util.List.of;

@ExtendWith(MockitoExtension.class)
class CancelExpiredReservationServiceShould {

    @InjectMocks
    private CancelExpiredReservationsService service;

    @Mock
    private BookRentalsPersistenceOutPort bookRentalsRepository;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

    private static final BookRentals BOOK_WITH_EXPIRED_RESERVATION = mock(BookRentals.class);

    @Test
    void cancelExpiredReservations() {
        given(bookRentalsRepository.findWithReservations()).willReturn(of(BOOK_WITH_EXPIRED_RESERVATION));

        service.cancelExpiredReservations();

        verify(BOOK_WITH_EXPIRED_RESERVATION).removeExpiredReservations(reservationExpirationPolicy);
        verify(bookRentalsRepository).save(BOOK_WITH_EXPIRED_RESERVATION);
    }
}
