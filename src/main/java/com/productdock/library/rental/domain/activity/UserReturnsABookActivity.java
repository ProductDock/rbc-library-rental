package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserReturnsABookActivity extends UserRentalActivity {

    protected UserReturnsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentals.BookCopyRentalState> changeStatusFrom(Optional<BookRentals.BookCopyRentalState> previousRecord) {
        if (previousRecord.isEmpty() || !previousRecord.get().isBorrow()) {
            throw new BookRentalException("Book is not rented by this user!");
        }
        return Optional.empty();
    }
}
