package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Date;

public class BookCopyMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus rentStatus = RentalStatus.RENTED;
    private static final RentalStatus reserveStatus = RentalStatus.RESERVED;

    public static BookRentalRecord.BookCopy bookCopyWithRentRequest() {
        return BookRentalRecord.BookCopy.builder()
                .patron(defaultUserEmail)
                .date(defaultDate)
                .status(rentStatus)
                .build();
    }

    public static BookRentalRecord.BookCopy bookCopyWithReserveRequest() {
        return BookRentalRecord.BookCopy.builder()
                .patron(defaultUserEmail)
                .date(defaultDate)
                .status(reserveStatus)
                .build();
    }

    public static BookRentalRecord.BookCopy bookCopyWithReserveRequest(Date reservationDate) {
        return BookRentalRecord.BookCopy.builder()
                .patron(defaultUserEmail)
                .date(reservationDate)
                .status(reserveStatus)
                .build();
    }
}
