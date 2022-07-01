package com.productdock.library.rental.domain.activity;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.exception.BookRentalException;

import java.util.Optional;

public class UserCancelsABookReservationActivity extends UserRentalActivity {

    protected UserCancelsABookReservationActivity(String initiator) {
        super(initiator);
    }

    @Override
    public Optional<BookRentals.BookCopyRentalState> changeStatusFrom(Optional<BookRentals.BookCopyRentalState> previousRecord) {
        if (previousRecord.isEmpty() || !previousRecord.get().isReservation()) {
            throw new BookRentalException("Book is not reserved by this user!");
        }
        return Optional.empty();
    }
}
