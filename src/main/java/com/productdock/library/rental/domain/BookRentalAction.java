package com.productdock.library.rental.domain;

import java.util.Optional;

public abstract class BookRentalAction {

    private final String userEmail;

    public BookRentalAction(String userEmail) {
        this.userEmail = userEmail;
    }

    public abstract Optional<BookCopyRentalRecord> performWithRespectTo(Optional<BookCopyRentalRecord> oldRecord);

    public String getInitiator() {
        return userEmail;
    }
}
