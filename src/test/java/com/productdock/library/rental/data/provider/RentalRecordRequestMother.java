package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.service.RentalRecordsMessage;
import com.productdock.library.rental.service.RentalStatus;

public class RentalRecordRequestMother {

    private static final String defaultPatron = "default@gmail.com";
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    public static RentalRecordsMessage.RentalRecordRequest defaultRentalRecordRequest() {
        return defaultRentalRecordRequestBuilder().build();
    }

    public static RentalRecordsMessage.RentalRecordRequest.RentalRecordRequestBuilder defaultRentalRecordRequestBuilder() {
        return RentalRecordsMessage.RentalRecordRequest.builder()
                .patron(defaultPatron)
                .status(defaultStatus);
    }
}
