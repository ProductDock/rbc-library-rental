package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserReservesABookActivity extends UserRentalActivity {

    protected UserReservesABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentals.BookCopyRentalState> changeStatusFrom(Optional<BookRentals.BookCopyRentalState> previousRecord) {
        if (previousRecord.isPresent()) {
            throw new BookRentalException("User has already reserved or rented this book");
        }
        BookRentals.BookCopyRentalState bookCopyRentalState = new BookRentals.BookCopyRentalState(getInitiator(), RentalStatus.RESERVED);
        return Optional.of(bookCopyRentalState);
    }
}
