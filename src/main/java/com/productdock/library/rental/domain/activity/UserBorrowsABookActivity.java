package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;

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
