package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserBorrowsABookActivity extends UserRentalActivity {

    protected UserBorrowsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentals.BookCopyRentalState> changeStatusFrom(Optional<BookRentals.BookCopyRentalState> previousRecord) {
        if (previousRecord.isPresent() && !previousRecord.get().isReservation()) {
            throw new BookRentalException("User has already rented this book!");
        }
        BookRentals.BookCopyRentalState bookCopyRentalState = new BookRentals.BookCopyRentalState(getInitiator(), RentalStatus.RENTED);
        return Optional.of(bookCopyRentalState);
    }
}
