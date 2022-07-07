package com.productdock.library.rental.data.provider.out.kafka;

import com.productdock.library.rental.adapter.out.kafka.BookRentalStatusChanged;
import com.productdock.library.rental.domain.RentalStatus;

public class MessageRentalRecordMother {

    private static final String defaultPatron = "default@gmail.com";
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookRentalStatusChanged.RentalRecord defaultRentalRecord() {
        return defaultRentalRecordBuilder().build();
    }

    public static BookRentalStatusChanged.RentalRecord.RentalRecordBuilder defaultRentalRecordBuilder() {
        return BookRentalStatusChanged.RentalRecord.builder()
                .patron(defaultPatron)
                .status(defaultStatus);
    }
}
