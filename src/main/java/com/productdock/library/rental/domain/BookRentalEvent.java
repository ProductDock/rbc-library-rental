package com.productdock.library.rental.domain;

import java.util.Date;

public class BookRentalEvent extends RentalAction {

    public final Date date;

    public BookRentalEvent(RentalAction rentalAction) {
        super(rentalAction.bookId, rentalAction.userId, rentalAction.action);
        this.date = new Date();
    }
}
