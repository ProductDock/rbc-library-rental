package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Date;

public class BookInteractionMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookCopyRentalState defaultBookInteraction() {
        return defaultBookInteractionBuilder().build();
    }

    public static BookCopyRentalState.BookCopyRentalStateBuilder defaultBookInteractionBuilder() {
        return BookCopyRentalState.builder()
                .userEmail(defaultUserEmail)
                .date(defaultDate)
                .status(defaultStatus);
    }
}
