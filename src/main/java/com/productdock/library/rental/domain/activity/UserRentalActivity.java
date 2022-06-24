package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;

import java.util.Optional;

public abstract class UserRentalActivity {

    private final String initiator;

    protected UserRentalActivity(String initiator) {
        this.initiator = initiator;
    }

    public String getInitiator() {
        return initiator;
    }

    public abstract Optional<BookRentals.BookCopyRentalState> changeStatusFrom(Optional<BookRentals.BookCopyRentalState> previousRecord);
}
