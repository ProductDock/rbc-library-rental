package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Date;

public class BookCopyMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus rentStatus = RentalStatus.RENTED;
    private static final RentalStatus reserveStatus = RentalStatus.RESERVED;

    public static BookRentals.BookCopyRentalState bookCopyWithRentRequest() {
        return BookRentals.BookCopyRentalState.builder()
                .patron(defaultUserEmail)
                .date(defaultDate)
                .status(rentStatus)
                .build();
    }

    public static BookRentals.BookCopyRentalState bookCopyWithReserveRequest() {
        return BookRentals.BookCopyRentalState.builder()
                .patron(defaultUserEmail)
                .date(defaultDate)
                .status(reserveStatus)
                .build();
    }

    public static BookRentals.BookCopyRentalState bookCopyWithReserveRequest(Date reservationDate) {
        return BookRentals.BookCopyRentalState.builder()
                .patron(defaultUserEmail)
                .date(reservationDate)
                .status(reserveStatus)
                .build();
    }
}
