package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyBuilder;
import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookRentalRecordMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookRentalRecordShould {

    @Mock
    private UserBookActivity userBookActivity;

    @Test
    void addRentWhenUserAlreadyReservedTheBook() {
        var bookRentalRecord = defaultBookRentalRecordBuilder().bookCopies(new ArrayList<>
                (Arrays.asList(defaultBookCopyWithRentRequest()))).build();
        System.out.println(bookRentalRecord);
        var reserveBookCopy = bookRentalRecord.getBookCopies().get(0);
        var rentBookCopy = defaultBookCopyWithRentRequest();
        given(userBookActivity.getInitiator()).willReturn(reserveBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(reserveBookCopy))).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies().get(0).getPatron()).isEqualTo(reserveBookCopy.getPatron());
        assertThat(bookRentalRecord.getBookCopies().get(0).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(1);
    }

    @Test
    void addReturnWhenUserRentedABookAlready() {
        var bookRentalRecord = defaultBookRentalRecordWithRentRequest();
        var rentBookCopy = bookRentalRecord.getBookCopies().get(0);
        given(userBookActivity.getInitiator()).willReturn(rentBookCopy.getPatron());
        given(userBookActivity.changeStatusFrom(Optional.of(rentBookCopy))).willReturn(Optional.empty());

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies()).isEmpty();
    }

    @Test
    void addRentWhenUserHadNotRentedOrReservedItAlready() {
        var bookRentalRecord = defaultBookRentalRecordWithReserveRequest();
        var rentBookCopy = defaultBookCopyBuilder().patron("newUser@gmail.com").build();
        given(userBookActivity.getInitiator()).willReturn("newUser@gmail.com");
        given(userBookActivity.changeStatusFrom(Optional.empty())).willReturn(Optional.of(rentBookCopy));

        bookRentalRecord.trackActivity(userBookActivity);

        assertThat(bookRentalRecord.getBookCopies().get(1).getPatron()).isEqualTo(rentBookCopy.getPatron());
        assertThat(bookRentalRecord.getBookCopies().get(1).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(2);
    }
}
