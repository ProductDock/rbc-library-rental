package com.productdock.library.rental.domain;

import com.productdock.library.rental.service.RentalStatus;

import java.util.Optional;

public class UserReservesABookActivity extends UserBookActivity {

    protected UserReservesABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isPresent()) {
            throw new RuntimeException("User has already reserved or rented this book");
        }
        BookRentalRecord.BookCopy bookCopy = new BookRentalRecord.BookCopy(getInitiator(), RentalStatus.RESERVED);
        return Optional.of(bookCopy);
    }
}
