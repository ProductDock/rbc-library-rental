package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.Optional;

public abstract class UserBookActivity {

    private final String initiator;

    protected UserBookActivity(String initiator) {
        this.initiator = initiator;
    }

    public String getInitiator() {
        return initiator;
    }

    public abstract Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord);
}
