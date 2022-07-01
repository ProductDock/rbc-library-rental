package com.productdock.library.rental.data.provider.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Date;

public class BookCopyRentalStateMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookCopyRentalState bookCopyRentalStateWithRequest(RentalStatus rentalStatus) {
        return BookCopyRentalState.builder()
                .userEmail(defaultUserEmail)
                .date(defaultDate)
                .status(rentalStatus)
                .build();
    };

    public static BookCopyRentalState defaultBookCopyRentalState() {
        return defaultBookCopyRentalStateBuilder().build();
    }

    public static BookCopyRentalState.BookCopyRentalStateBuilder defaultBookCopyRentalStateBuilder() {
        return BookCopyRentalState.builder()
                .userEmail(defaultUserEmail)
                .date(defaultDate)
                .status(defaultStatus);
    }
}
