package com.productdock.library.rental.domain;

import java.util.Optional;

public abstract class UserBookActivity {

    private final String initiator;

    protected UserBookActivity(String initiator) {
        this.initiator = initiator;
    }

    protected String getInitiator() {
        return initiator;
    }

    abstract Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord);
}
