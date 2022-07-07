package com.productdock.library.rental.data.provider.out.kafka;

import com.productdock.library.rental.adapter.out.kafka.BookRentalStatusChanged;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.out.kafka.MessageRentalRecordMother.defaultRentalRecord;

public class BookRentalStatusChangedMother {

    private static final String defaultBookId = "1";

    private static final List<BookRentalStatusChanged.RentalRecord> defaultRentalRecords =
            new ArrayList<>(Arrays.asList(defaultRentalRecord()));

    public static BookRentalStatusChanged defaultRentalRecordsMessage() {
        return defaultRentalRecordsMessageBuilder().build();
    }

    public static BookRentalStatusChanged.BookRentalStatusChangedBuilder defaultRentalRecordsMessageBuilder() {
        return BookRentalStatusChanged.builder()
                .bookId(defaultBookId)
                .rentalRecords(defaultRentalRecords);
    }
}
