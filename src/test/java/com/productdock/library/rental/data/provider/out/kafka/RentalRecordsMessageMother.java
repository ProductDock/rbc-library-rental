package com.productdock.library.rental.data.provider.out.kafka;

import com.productdock.library.rental.adapter.out.kafka.BookRentalsMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.out.kafka.MessageRentalRecordMother.defaultRentalRecord;

public class RentalRecordsMessageMother {

    private static final String defaultBookId = "1";

    private static final List<BookRentalsMessage.RentalRecord> defaultRentalRecords =
            new ArrayList<>(Arrays.asList(defaultRentalRecord()));

    public static BookRentalsMessage defaultRentalRecordsMessage() {
        return defaultRentalRecordsMessageBuilder().build();
    }

    public static BookRentalsMessage.BookRentalsMessageBuilder defaultRentalRecordsMessageBuilder() {
        return BookRentalsMessage.builder()
                .bookId(defaultBookId)
                .rentalRecords(defaultRentalRecords);
    }
}
