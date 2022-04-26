package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalAction;

import java.util.Date;
import java.util.Optional;

public class ReserveABookAction extends BookRentalAction {

    public ReserveABookAction(String userEmail) {
        super(userEmail);
    }

    @Override
    public Optional<BookCopyRentalRecord> performWithRespectTo(Optional<BookCopyRentalRecord> oldRecord) {
        if (oldRecord.isPresent()) {
            throw new RuntimeException();
        }
        var record = new BookCopyRentalRecord(RentalAction.RESERVE, getInitiator(), new Date());
        return Optional.of(record);
    }
}
