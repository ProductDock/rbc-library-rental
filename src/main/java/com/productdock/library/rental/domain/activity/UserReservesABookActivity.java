package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserReservesABookActivity extends UserBookActivity {

    protected UserReservesABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isPresent()) {
            throw new BookRentalException("User has already reserved or rented this book");
        }
        BookRentalRecord.BookCopy bookCopy = new BookRentalRecord.BookCopy(getInitiator(), RentalStatus.RESERVED);
        return Optional.of(bookCopy);
    }
}
