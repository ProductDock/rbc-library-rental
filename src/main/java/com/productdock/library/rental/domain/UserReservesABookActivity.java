package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalStatus;

import java.util.Optional;

public class UserReservesABookActivity extends UserBookActivity {

    protected UserReservesABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> executeWithRespectTo(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isPresent()) {
            throw new RuntimeException("User has already reserved or rented this book");
        }
        BookRentalRecord.BookCopy bookCopy = new BookRentalRecord.BookCopy(getInitiator(), RentalStatus.RESERVE);
        return Optional.of(bookCopy);
    }
}
