package com.productdock.library.rental.domain;

import com.productdock.library.rental.scheduled.ReservationExpirationPolicy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;
import static com.productdock.library.rental.data.provider.BookRentalRecordMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookRentalRecordShould {

    @Mock
    private UserBookActivity userBookActivity;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

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
        var firstDate = new Date(1655287200000L);
        var secondDate = new Date(1655632800000L);
        var bookCopies = new ArrayList<>(Arrays.asList(
                bookCopyWithReserveRequest(firstDate),
                bookCopyWithReserveRequest(secondDate))
        );
        var bookRentalRecord = bookRentalRecordBuilder().bookCopies(bookCopies).build();


        given(reservationExpirationPolicy.isReservationExpired(firstDate)).willReturn(false);
        given(reservationExpirationPolicy.isReservationExpired(firstDate)).willReturn(true);

        bookRentalRecord.removeExpiredReservations(reservationExpirationPolicy);

        assertThat(bookRentalRecord.getBookCopies()).hasSize(1);
    }
}
