package com.productdock.library.rental.domain;

import java.util.Optional;

public class ReturnABookAction extends BookRentalAction {

    public ReturnABookAction(String userEmail) {
        super(userEmail);
    }

    @Override
    public Optional<BookCopyRentalRecord> performWithRespectTo(Optional<BookCopyRentalRecord> oldRecord) {
        if (oldRecord.isEmpty()) {
            throw new RuntimeException();
        }
        return Optional.empty();
    }
}
