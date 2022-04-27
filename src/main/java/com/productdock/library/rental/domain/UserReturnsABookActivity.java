package com.productdock.library.rental.domain;

import java.util.Optional;

public class UserReturnsABookActivity extends UserBookActivity {

    protected UserReturnsABookActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> executeWithRespectTo(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isEmpty() || !previousRecord.get().isBorrow()) {
            throw new RuntimeException("Book is not rented by this user!");
        }
        return Optional.empty();
    }
}
