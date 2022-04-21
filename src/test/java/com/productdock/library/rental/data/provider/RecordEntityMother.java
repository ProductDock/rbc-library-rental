package com.productdock.library.rental.data.provider;


import com.productdock.library.rental.book.BookInteraction;
import com.productdock.library.rental.record.RecordEntity;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;

public class RecordEntityMother {

    private static final String defaultBookId = "1";
    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();


    private static final List<BookInteraction> defaultReservations = new LinkedList<BookInteraction>
            (Arrays.asList(new BookInteraction(defaultUserEmail, defaultDate)));
    private static final List<BookInteraction> defaultRents = new LinkedList<BookInteraction>
            (Arrays.asList(new BookInteraction(defaultUserEmail, defaultDate)));

    public static RecordEntity defaultRecordEntity() {
        return defaultRecordEntityBuilder().build();
    }

    public static RecordEntity.RecordEntityBuilder defaultRecordEntityBuilder() {
        return RecordEntity.builder()
                .bookId(defaultBookId)
                .rents(defaultRents)
                .reservations(defaultReservations);
    }
}
