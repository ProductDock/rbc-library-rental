package com.productdock.library.rental.domain;

import com.productdock.library.rental.exception.BookRentalException;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Optional;

public class UserBorrowsABookActivity extends UserBookActivity {

    protected UserBorrowsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isPresent() && !previousRecord.get().isReservation()) {
            throw new BookRentalException("User has already rented this book!");
        }
        BookRentalRecord.BookCopy bookCopy = new BookRentalRecord.BookCopy(getInitiator(), RentalStatus.RENTED);
        return Optional.of(bookCopy);
    }
}
