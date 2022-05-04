package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookRentalRecordMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookRentalRecordShould {

    @Mock
    private UserBookActivity userBookActivity;

    @Test
    void addRentWhenUserAlreadyReservedTheBook() {
        var bookRentalRecord = bookRentalRecordWithReserveRequest();
        System.out.println(bookRentalRecord);
        var reserveBookCopy = bookRentalRecord.getBookCopies().get(0);
        var rentBookCopy = bookCopyWithRentRequest();
        given(userBookActivity.getInitiator()).willReturn(reserveBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(reserveBookCopy))).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies().get(0).getPatron()).isEqualTo(reserveBookCopy.getPatron());
        assertThat(bookRentalRecord.getBookCopies().get(0).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(1);
    }

    @Test
    void addReturnWhenUserRentedABookAlready() {
        var bookRentalRecord = bookRentalRecordWithRentRequest();
        var rentBookCopy = bookRentalRecord.getBookCopies().get(0);
        given(userBookActivity.getInitiator()).willReturn(rentBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(rentBookCopy))).willReturn(Optional.empty());

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).isEmpty();
    }

    @Test
    void addRentWhenUserHadNotRentedOrReservedItAlready() {
        var bookRentalRecord = bookRentalRecordWithReserveRequest();
        var rentBookCopy = bookCopyWithRentRequest();
        rentBookCopy.setPatron("newUser@gmail.com");
        given(userBookActivity.getInitiator()).willReturn("newUser@gmail.com");
        given(userBookActivity.changeStatusFrom(Optional.empty())).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies().get(1).getPatron()).isEqualTo(rentBookCopy.getPatron());
        assertThat(bookRentalRecord.getBookCopies().get(1).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(2);
    }
}
