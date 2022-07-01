package com.productdock.library.rental.domain;

import com.productdock.library.rental.domain.activity.UserRentalActivity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.rentedBookCopy;
import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.reservedBookCopyBuilder;
import static com.productdock.library.rental.data.provider.domain.BookRentalsMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class BookRentalsShould {

    @Mock
    private UserRentalActivity userRentalActivity;

    @Mock
    private ReservationExpirationPolicy reservationExpirationPolicy;

    private static final Date NOT_EXPIRED_DATE = mock(Date.class);
    private static final Date EXPIRED_DATE = mock(Date.class);
    private static final BookRentals.BookCopyRentalState EXPIRED_RESERVATION = reservedBookCopyBuilder().date(EXPIRED_DATE).build();
    private static final BookRentals.BookCopyRentalState NOT_EXPIRED_RESERVATION = reservedBookCopyBuilder().date(NOT_EXPIRED_DATE).build();

    @Test
    void addRentRecord_whenUserAlreadyReservedTheBook() {
        var bookRentalsWithReserveRequest = bookRentalsWithReserveRequest();
        var bookCopyRentalState = bookRentalsWithReserveRequest.getBookCopiesRentalState().get(0);
        var bookCopyRentalStateWithRentRequest = rentedBookCopy();

        given(userRentalActivity.getInitiator()).willReturn(bookCopyRentalState.getPatron());
        given(userRentalActivity.changeStatusFrom(Optional.of(bookCopyRentalState)))
                .willReturn(Optional.of(bookCopyRentalStateWithRentRequest));

        bookRentalsWithReserveRequest.trackRentalActivity(userRentalActivity);

        assertThat(bookRentalsWithReserveRequest.getBookCopiesRentalState()).containsOnly(bookCopyRentalStateWithRentRequest);
    }

    @Test
    void addReturnRecord_whenUserRentedABookAlready() {
        var bookRentalsWithRentRequest = bookRentalsWithRentRequest();
        var bookCopyRentalState = bookRentalsWithRentRequest.getBookCopiesRentalState().get(0);

        given(userRentalActivity.getInitiator()).willReturn(bookCopyRentalState.getPatron());
        given(userRentalActivity.changeStatusFrom(Optional.of(bookCopyRentalState))).willReturn(Optional.empty());

        bookRentalsWithRentRequest.trackRentalActivity(userRentalActivity);

        assertThat(bookRentalsWithRentRequest.getBookCopiesRentalState()).isEmpty();
    }

    @Test
    void addCancelBookReservationRecord_whenUserReservedABookAlready() {
        var bookRentalsWithReserveRequest = bookRentalsWithReserveRequest();
        var bookCopyRentalState = bookRentalsWithReserveRequest.getBookCopiesRentalState().get(0);

        given(userRentalActivity.getInitiator()).willReturn(bookCopyRentalState.getPatron());
        given(userRentalActivity.changeStatusFrom(Optional.of(bookCopyRentalState))).willReturn(Optional.empty());

        bookRentalsWithReserveRequest.trackRentalActivity(userRentalActivity);

        assertThat(bookRentalsWithReserveRequest.getBookCopiesRentalState()).isEmpty();
    }

    @Test
    void addRentRecord_whenUserHadNotRentedOrReservedItAlready() {
        var bookRentalsWithNoRequests = bookRentalsWithNoRequests();
        var bookCopyRentalState = rentedBookCopy();
        bookCopyRentalState.setPatron("newUser@gmail.com");

        given(userRentalActivity.getInitiator()).willReturn("newUser@gmail.com");
        given(userRentalActivity.changeStatusFrom(Optional.empty())).willReturn(Optional.of(bookCopyRentalState));

        bookRentalsWithNoRequests.trackRentalActivity(userRentalActivity);

        assertThat(bookRentalsWithNoRequests.getBookCopiesRentalState()).containsOnly(bookCopyRentalState);
    }

    @Test
    void removeExpiredReservations() {
        var bookCopies = new ArrayList<>(Arrays.asList(
                EXPIRED_RESERVATION,
                NOT_EXPIRED_RESERVATION
        ));
        var bookRentals = bookRentalRecordBuilder().bookCopiesRentalState(bookCopies).build();

        given(reservationExpirationPolicy.isReservationExpired(EXPIRED_DATE)).willReturn(true);
        given(reservationExpirationPolicy.isReservationExpired(NOT_EXPIRED_DATE)).willReturn(false);

        bookRentals.removeExpiredReservations(reservationExpirationPolicy);

        assertThat(bookRentals.getBookCopiesRentalState()).containsOnly(NOT_EXPIRED_RESERVATION);
    }
}
