package com.productdock.library.rental.domain;

import com.productdock.library.rental.application.service.ReservationExpirationPolicy;
import com.productdock.library.rental.domain.activity.UserBookActivity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;
import static com.productdock.library.rental.data.provider.BookRentalRecordMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookRentalRecordShould {

    @Mock
    private UserBookActivity userBookActivity;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

    private static final Date NOT_EXPIRED_DATE = mock(Date.class);
    private static final Date EXPIRED_DATE = mock(Date.class);
    private static final BookRentalRecord.BookCopy EXPIRED_RESERVATION = bookCopyWithReserveRequest(EXPIRED_DATE);
    private static final BookRentalRecord.BookCopy NOT_EXPIRED_RESERVATION = bookCopyWithReserveRequest(NOT_EXPIRED_DATE);

    @Test
    void addRentRecord_whenUserAlreadyReservedTheBook() {
        var bookRentalRecord = bookRentalRecordWithReserveRequest();
        var reserveBookCopy = bookRentalRecord.getBookCopies().get(0);
        var rentBookCopy = bookCopyWithRentRequest();
        given(userBookActivity.getInitiator()).willReturn(reserveBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(reserveBookCopy))).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).containsOnly(rentBookCopy);
    }

    @Test
    void addReturnRecord_whenUserRentedABookAlready() {
        var bookRentalRecord = bookRentalRecordWithRentRequest();
        var rentBookCopy = bookRentalRecord.getBookCopies().get(0);
        given(userBookActivity.getInitiator()).willReturn(rentBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(rentBookCopy))).willReturn(Optional.empty());

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).isEmpty();
    }

    @Test
    void addCancelBookReservationRecord_whenUserReservedABookAlready() {
        var bookRentalRecord = bookRentalRecordWithReserveRequest();
        var reserveBookCopy = bookRentalRecord.getBookCopies().get(0);
        given(userBookActivity.getInitiator()).willReturn(reserveBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(reserveBookCopy))).willReturn(Optional.empty());

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).isEmpty();
    }

    @Test
    void addRentRecord_whenUserHadNotRentedOrReservedItAlready() {
        var bookRentalRecord = bookRentalRecordWithNoRequests();
        var rentBookCopy = bookCopyWithRentRequest();
        rentBookCopy.setPatron("newUser@gmail.com");
        given(userBookActivity.getInitiator()).willReturn("newUser@gmail.com");
        given(userBookActivity.changeStatusFrom(Optional.empty())).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).containsOnly(rentBookCopy);
    }

    @Test
    void removeExpiredReservations() {
        var bookCopies = new ArrayList<>(Arrays.asList(
                EXPIRED_RESERVATION,
                NOT_EXPIRED_RESERVATION
        ));
        var bookRentalRecord = bookRentalRecordBuilder().bookCopies(bookCopies).build();

        given(reservationExpirationPolicy.isReservationExpired(EXPIRED_DATE)).willReturn(true);
        given(reservationExpirationPolicy.isReservationExpired(NOT_EXPIRED_DATE)).willReturn(false);

        bookRentalRecord.removeExpiredReservations(reservationExpirationPolicy);

        assertThat(bookRentalRecord.getBookCopies()).containsOnly(NOT_EXPIRED_RESERVATION);
    }
}
