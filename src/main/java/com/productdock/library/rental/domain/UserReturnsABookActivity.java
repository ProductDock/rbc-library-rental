package com.productdock.library.rental.domain;

import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserReturnsABookActivity extends UserBookActivity {

    protected UserReturnsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isEmpty() || !previousRecord.get().isBorrow()) {
            throw new BookRentalException("Book is not rented by this user!");
        }
        return Optional.empty();
    }
}
