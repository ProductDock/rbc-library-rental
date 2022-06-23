package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserCancelsABookReservationActivity extends UserBookActivity {

    protected UserCancelsABookReservationActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentalRecord.BookCopy> changeStatusFrom(Optional<BookRentalRecord.BookCopy> previousRecord) {
        if (previousRecord.isEmpty() || !previousRecord.get().isReservation()) {
            throw new BookRentalException("Book is not reserved by this user!");
        }
        return Optional.empty();
    }
}
