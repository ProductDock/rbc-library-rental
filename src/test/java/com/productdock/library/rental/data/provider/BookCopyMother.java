package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Date;

public class BookCopyMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus rentStatus = RentalStatus.RENTED;
    private static final RentalStatus reserveStatus = RentalStatus.RESERVED;

    public static BookRentalRecord.BookCopy defaultBookCopyWithRentRequest() {
        return defaultBookCopyBuilder().status(rentStatus).build();
    }

    public static BookRentalRecord.BookCopy defaultBookCopyWithReserveRequest() {
        return defaultBookCopyBuilder().status(reserveStatus).build();
    }

    public static BookRentalRecord.BookCopy.BookCopyBuilder defaultBookCopyBuilder() {
        return BookRentalRecord.BookCopy.builder()
                .patron(defaultUserEmail)
                .date(defaultDate)
                .status(rentStatus);
    }
}
