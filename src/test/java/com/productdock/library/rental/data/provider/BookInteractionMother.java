package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookInteraction;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.Date;

public class BookInteractionMother {

    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookInteraction defaultBookInteraction() {
        return defaultBookInteractionBuilder().build();
    }

    public static BookInteraction.BookInteractionBuilder defaultBookInteractionBuilder() {
        return BookInteraction.builder()
                .userEmail(defaultUserEmail)
                .date(defaultDate)
                .status(defaultStatus);
    }
}
