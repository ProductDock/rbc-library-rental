package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;

import java.util.Optional;

public class UserBorrowsABookActivity extends UserBookActivity {

    protected UserBorrowsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> executeWithRespectTo(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isPresent() && !previousRecord.get().isReservation()) {
            throw new RuntimeException("User has already rented this book!");
        }
        BookRentalRecord.BookCopy bookCopy = new BookRentalRecord.BookCopy(getInitiator(), RentalStatus.RENT);
        return Optional.of(bookCopy);
    }
}
