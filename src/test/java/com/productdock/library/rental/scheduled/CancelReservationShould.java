package com.productdock.library.rental.scheduled;

import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalRecordService;
import com.productdock.library.rental.service.RentalRequestDto;
import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.productdock.library.rental.data.provider.BookInteractionMother.defaultBookInteractionBuilder;
import static com.productdock.library.rental.data.provider.RentalRecordEntityMother.defaultRentalRecordEntityBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CancelReservationShould {

    @InjectMocks
    private CancelReservation cancelReservation;

    @Mock
    private RentalRecordService rentalRecordService;

    @Mock
    private DateProvider dateProvider;

    @Mock
    private ReservationHoldPolicy reservationHoldPolicy;

    private static final Date DATE_13_06_2022 = new Date(1655107200000L);
    private static final Date DATE_15_06_2022 = new Date(1655287200000L);
    private static final Date DATE_17_06_2022 = new Date(1655460000000L);
    private static final Date DATE_20_06_2022 = new Date(1655719200000L);
    private static final Date DATE_21_06_2022 = new Date(1655805600000L);

    private static final BookInteraction WEDNESDAY_RESERVED_INTERACTION = defaultBookInteractionBuilder().date(DATE_15_06_2022).status(RentalStatus.RESERVED).build();
    private static final BookInteraction MONDAY_RESERVED_INTERACTION = defaultBookInteractionBuilder().date(DATE_13_06_2022).status(RentalStatus.RESERVED).build();

    private static final RentalRecordEntity FIRST_RECORD = defaultRentalRecordEntityBuilder().bookId("1")
            .interaction(MONDAY_RESERVED_INTERACTION).build();
    private static final RentalRecordEntity SECOND_RECORD = defaultRentalRecordEntityBuilder().bookId("2")
            .interaction(WEDNESDAY_RESERVED_INTERACTION).build();

    private static final Collection<RentalRecordEntity> ANY_RENTAL_RECORD_COLLECTION = List.of(FIRST_RECORD, SECOND_RECORD);

    @BeforeEach
    public void setUp() {
        given(reservationHoldPolicy.getTimeUnit()).willReturn(TimeUnit.DAYS);
        given(reservationHoldPolicy.getLimit()).willReturn(4);
        given(reservationHoldPolicy.isSkippedWeekend()).willReturn(true);
    }

    @Test
    void executeScheduledWhenWeekendNotIncludedAndTimeToCancel() {
        given(dateProvider.now()).willReturn(DATE_17_06_2022);
        given(rentalRecordService.findAllReserved()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        cancelReservation.schedule();

        var rentalRequest = ArgumentCaptor.forClass(RentalRequestDto.class);

        verify(rentalRecordService, times(1)).create(rentalRequest.capture(), eq(MONDAY_RESERVED_INTERACTION.getUserEmail()));
        assertThat(rentalRequest.getValue().bookId).isEqualTo(FIRST_RECORD.getBookId());
        assertThat(rentalRequest.getValue().requestedStatus).isEqualTo(RentalStatus.CANCELED);
    }


    @Test
    void executeScheduledWhenWeekendNotIncludedAndNotTimeToCancel() {
        given(dateProvider.now()).willReturn(DATE_15_06_2022);
        given(rentalRecordService.findAllReserved()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        cancelReservation.schedule();

        verify(rentalRecordService, never()).create(any(), any());
    }

    @Test
    void executeScheduledWhenWeekendIncludedAndTimeToCancel() {
        given(dateProvider.now()).willReturn(DATE_21_06_2022);
        given(rentalRecordService.findAllReserved()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        cancelReservation.schedule();

        var rentalRequest = ArgumentCaptor.forClass(RentalRequestDto.class);

        verify(rentalRecordService, times(2)).create(rentalRequest.capture(), eq(MONDAY_RESERVED_INTERACTION.getUserEmail()));

        assertThat(rentalRequest.getAllValues().get(0).bookId).isEqualTo(FIRST_RECORD.getBookId());
        assertThat(rentalRequest.getAllValues().get(0).requestedStatus).isEqualTo(RentalStatus.CANCELED);
        assertThat(rentalRequest.getAllValues().get(1).bookId).isEqualTo(SECOND_RECORD.getBookId());
        assertThat(rentalRequest.getAllValues().get(1).requestedStatus).isEqualTo(RentalStatus.CANCELED);
    }

    @Test
    void executeScheduledWhenWeekendIncludedAndNotTimeToCancel() {
        given(dateProvider.now()).willReturn(DATE_20_06_2022);
        given(rentalRecordService.findAllReserved()).willReturn(ANY_RENTAL_RECORD_COLLECTION);

        cancelReservation.schedule();

        var rentalRequest = ArgumentCaptor.forClass(RentalRequestDto.class);

        verify(rentalRecordService, times(1)).create(rentalRequest.capture(), eq(MONDAY_RESERVED_INTERACTION.getUserEmail()));

        assertThat(rentalRequest.getValue().bookId).isEqualTo(FIRST_RECORD.getBookId());
        assertThat(rentalRequest.getValue().requestedStatus).isEqualTo(RentalStatus.CANCELED);
    }

}
