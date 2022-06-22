package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.service.RentalRecordService;
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
class CancelReservationShould {

    @InjectMocks
    private CancelReservationJob cancelReservation;

    @Mock
    private RentalRecordService rentalRecordService;

    @Mock
    private ReservationExpirationPolicy reservationHoldPolicy;

    private static final BookRentalRecord ANY_RENTAL_RECORD = mock(BookRentalRecord.class);
    private static final Collection<BookRentalRecord> ANY_RENTAL_RECORD_COLLECTION = List.of(ANY_RENTAL_RECORD);

    @Test
    void executeScheduledTask() {
        given(rentalRecordService.findAllReserved()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        cancelReservation.cancelReservation();

        verify(ANY_RENTAL_RECORD).removeExpiredReservations(reservationHoldPolicy);
        verify(rentalRecordService).saveRentalRecord(ANY_RENTAL_RECORD);
    }
}
