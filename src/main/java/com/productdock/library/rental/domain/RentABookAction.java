package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalAction;

import java.util.Date;
import java.util.Optional;

public class RentABookAction extends BookRentalAction {

    public RentABookAction(String userEmail) {
        super(userEmail);
    }

    @Override
    public Optional<BookCopyRentalRecord> performWithRespectTo(Optional<BookCopyRentalRecord> oldRecord) {
        if (oldRecord.isEmpty()
                || oldRecord.get().isReservation()) {
            var record = new BookCopyRentalRecord(RentalAction.RENT, getInitiator(), new Date());
            return Optional.of(record);
        }
        throw new RuntimeException();
    }

}
