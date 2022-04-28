package com.productdock.library.rental.data.provider;


import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.service.RentalRecordEntity;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class RentalRecordEntityMother {

    private static final String defaultBookId = "1";
    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    private static final List<BookInteraction> defaultInteractions = new LinkedList<>
            (Arrays.asList(new BookInteraction(defaultUserEmail, defaultDate, defaultStatus)));

    public static RentalRecordEntity defaultRentalRecordEntity() {
        return defaultRentalRecordEntityBuilder().build();
    }

    public static RentalRecordEntity.RentalRecordEntityBuilder defaultRentalRecordEntityBuilder() {
        return RentalRecordEntity.builder()
                .bookId(defaultBookId)
                .interactions(defaultInteractions);
    }
}
