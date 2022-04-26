package com.productdock.library.rental.domain;

import com.productdock.library.rental.record.RentalAction;

import java.util.Date;

public final class BookCopyRentalRecord {

    private final RentalAction rentalAction;
    private final String userEmail;
    private final Date date;

    public BookCopyRentalRecord(RentalAction rentalAction, String userEmail, Date date) {
        this.rentalAction = rentalAction;
        this.userEmail = userEmail;
        this.date = date;
    }

    public boolean isRent() {
        return rentalAction.equals(RentalAction.RENT);
    }

    public boolean isReservation() {
        return rentalAction.equals(RentalAction.RESERVE);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Date getDate() {
        return date;
    }

}
