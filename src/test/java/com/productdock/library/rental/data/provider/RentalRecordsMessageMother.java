package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.service.RentalRecordsMessage;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class RentalRecordsMessageMother {

    private static final String defaultBookId = "1";
    private static final String defaultPatron = "default@gmail.com";
    private static final RentalStatus defaultStatus = RentalStatus.RENTED;

    private static final List<RentalRecordsMessage.RentalRecordRequest> defaultRentalRecords = new LinkedList<>(Arrays.asList(new RentalRecordsMessage.RentalRecordRequest(defaultPatron, defaultStatus)));

    public static RentalRecordsMessage defaultRentalRecordsMessage() {
        return defaultRentalRecordsMessageBuilder().build();
    }

    public static RentalRecordsMessage.RentalRecordsMessageBuilder defaultRentalRecordsMessageBuilder() {
        return RentalRecordsMessage.builder().bookId(defaultBookId).rentalRecords(defaultRentalRecords);
    }
}
