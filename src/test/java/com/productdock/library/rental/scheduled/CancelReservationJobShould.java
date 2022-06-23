//package com.productdock.library.rental.scheduled;
//
//import com.productdock.library.rental.application.service.CancelExpiredReservationsService;
//import com.productdock.library.rental.application.service.GetRentalRecordService;
//import com.productdock.library.rental.application.service.ReservationExpirationPolicy;
//import com.productdock.library.rental.domain.BookRentalRecord;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Collection;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//
//@ExtendWith(MockitoExtension.class)
//class CancelReservationJobShould {
//
//    @InjectMocks
//    private CancelExpiredReservationsService job;
//
//    @Mock
//    private GetRentalRecordService getRentalRecordService;
//
//    @Mock
//    private ReservationExpirationPolicy reservationHoldPolicy;
//
//    private static final BookRentalRecord ANY_RENTAL_RECORD = mock(BookRentalRecord.class);
//    private static final Collection<BookRentalRecord> ANY_RENTAL_RECORD_COLLECTION = List.of(ANY_RENTAL_RECORD);
//
//    @Test
//    void executeScheduledTask() {
//        given(getRentalRecordService.findWithReservations()).willReturn(ANY_RENTAL_RECORD_COLLECTION);
//
//        job.cancelExpiredReservations();
//
//        verify(ANY_RENTAL_RECORD).removeExpiredReservations(reservationHoldPolicy);
//        verify(getRentalRecordService).saveRentalRecord(ANY_RENTAL_RECORD);
//    }
//}
