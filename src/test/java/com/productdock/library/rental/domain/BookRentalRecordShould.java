package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;

import static com.productdock.library.rental.data.provider.BookRentalRecordMother.defaultBookRentalRecordWithRentRequest;
import static com.productdock.library.rental.data.provider.BookRentalRecordMother.defaultBookRentalRecordWithReserveRequest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class BookRentalRecordShould {

    private final String newUser = "example@gmail.com";
    private final String userWhoAlreadyInteractedWithABook = "default@gmail.com";

    @Test
    void addARentStatusWhenOtherUsersRentStatusAlreadyExists() {
        var activity = new UserBorrowsABookActivity(newUser);
        var bookRentalRecord = defaultBookRentalRecordWithRentRequest();
        bookRentalRecord.trackActivity(activity);
        assertThat(bookRentalRecord.getBookCopies().get(1).getPatron()).isEqualTo(newUser);
        assertThat(bookRentalRecord.getBookCopies().get(1).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(2);
    }

    @Test
    void addRentWhenUserAlreadyReservedTheBook() {
        var activity = new UserBorrowsABookActivity(userWhoAlreadyInteractedWithABook);
        var bookRentalRecord = defaultBookRentalRecordWithReserveRequest();
        bookRentalRecord.trackActivity(activity);
        assertThat(bookRentalRecord.getBookCopies().get(0).getPatron()).isEqualTo(userWhoAlreadyInteractedWithABook);
        assertThat(bookRentalRecord.getBookCopies().get(0).getStatus()).isEqualTo(RentalStatus.RENTED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(1);
    }

    @Test
    void addReturnABookWhenUserHasAlreadyRentedIt() {
        var activity = new UserReturnsABookActivity(userWhoAlreadyInteractedWithABook);
        var bookRentalRecord = new BookRentalRecord("1", new LinkedList<>
                (Arrays.asList(new BookRentalRecord.BookCopy(new Date(), userWhoAlreadyInteractedWithABook, RentalStatus.RENTED))));
        System.out.println(bookRentalRecord);
        bookRentalRecord.trackActivity(activity);
        assertThat(bookRentalRecord.getBookCopies()).isEmpty();
    }

    @Test
    void addAReserveStatusWhenUserDidNotReserveOrRentABook() {
        var activity = new UserReservesABookActivity(newUser);
        var bookRentalRecord = defaultBookRentalRecordWithRentRequest();
        bookRentalRecord.trackActivity(activity);
        assertThat(bookRentalRecord.getBookCopies().get(1).getPatron()).isEqualTo(newUser);
        assertThat(bookRentalRecord.getBookCopies().get(1).getStatus()).isEqualTo(RentalStatus.RESERVED);
        assertThat(bookRentalRecord.getBookCopies()).hasSize(2);
    }

    @Test
    void addRentRequestWhenOneRentRequestFromTheSameUserAlreadyExists() {
        var activity = new UserBorrowsABookActivity(userWhoAlreadyInteractedWithABook);
        var bookRentalRecord = defaultBookRentalRecordWithRentRequest();
        assertThatThrownBy(() -> bookRentalRecord.trackActivity(activity))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void addReserveRequestWhenOneReserveRequestFromTheSameUserAlreadyExists() {
        var activity = new UserReservesABookActivity(userWhoAlreadyInteractedWithABook);
        var bookRentalRecord = defaultBookRentalRecordWithReserveRequest();
        assertThatThrownBy(() -> bookRentalRecord.trackActivity(activity))
                .isInstanceOf(RuntimeException.class);
    }
}
