package com.productdock.library.rental.data.provider.out.kafka;

import com.productdock.library.rental.adapter.out.kafka.BookRentalsMessage;
import com.productdock.library.rental.domain.RentalStatus;

public class MessageRentalRecordMother {

    private static final String defaultPatron = "default@gmail.com";
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookRentalsMessage.RentalRecord defaultRentalRecord() {
        return defaultRentalRecordBuilder().build();
    }

    public static BookRentalsMessage.RentalRecord.RentalRecordBuilder defaultRentalRecordBuilder() {
        return BookRentalsMessage.RentalRecord.builder()
                .patron(defaultPatron)
                .status(defaultStatus);
    }
}
