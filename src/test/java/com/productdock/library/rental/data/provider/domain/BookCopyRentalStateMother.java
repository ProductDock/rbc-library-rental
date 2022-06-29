package com.productdock.library.rental.data.provider.domain;

import com.productdock.library.rental.domain.BookRentals;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Date;

public class BookCopyRentalStateMother {

    private static final String defaultPatron = "default@gmail.com";
    private static final Date defaultDate = new Date();

    public static BookRentals.BookCopyRentalState rentedBookCopy() {
        return BookRentals.BookCopyRentalState.builder()
                .patron(defaultPatron)
                .date(defaultDate)
                .status(RentalStatus.RENTED)
                .build();
    }

    public static BookRentals.BookCopyRentalState reservedBookCopy() {
        return reservedBookCopyBuilder().build();
    }

    public static BookRentals.BookCopyRentalState.BookCopyRentalStateBuilder reservedBookCopyBuilder() {
        return BookRentals.BookCopyRentalState.builder()
                .patron(defaultPatron)
                .date(defaultDate)
                .status(RentalStatus.RESERVED);
    }
}
