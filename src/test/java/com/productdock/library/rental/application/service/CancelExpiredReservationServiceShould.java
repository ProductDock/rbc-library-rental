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

@ExtendWith(MockitoExtension.class)
class CancelExpiredReservationServiceShould {

    @InjectMocks
    private CancelExpiredReservationsService service;

    @Mock
    private BookRentalsPersistenceOutPort bookRentalsRepository;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

    private static final BookRentals ANY_RENTAL_RECORD = mock(BookRentals.class);
    private static final Collection<BookRentals> ANY_RENTAL_RECORD_COLLECTION = List.of(ANY_RENTAL_RECORD);

    @Test
    void cancelExpiredReservations() {
        given(bookRentalsRepository.findWithReservations()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        service.cancelExpiredReservations();

        verify(ANY_RENTAL_RECORD).removeExpiredReservations(reservationExpirationPolicy);
        verify(bookRentalsRepository).save(ANY_RENTAL_RECORD);
    }
}
