package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.adapter.out.kafka.BookRentalsMessage;
import com.productdock.library.rental.domain.RentalStatus;

public class RentalRecordRequestMother {

    private static final String defaultPatron = "default@gmail.com";
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static BookRentalsMessage.RentalRecordRequest defaultRentalRecordRequest() {
        return defaultRentalRecordRequestBuilder().build();
    }

    public static BookRentalsMessage.RentalRecordRequest.RentalRecordRequestBuilder defaultRentalRecordRequestBuilder() {
        return BookRentalsMessage.RentalRecordRequest.builder()
                .patron(defaultPatron)
                .status(defaultStatus);
    }
}
