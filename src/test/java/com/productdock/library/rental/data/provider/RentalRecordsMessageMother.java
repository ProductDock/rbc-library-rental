package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.adapter.out.kafka.RentalRecordsMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.RentalRecordRequestMother.defaultRentalRecordRequest;

public class RentalRecordsMessageMother {

    private static final String defaultBookId = "1";

    private static final List<RentalRecordsMessage.RentalRecordRequest> defaultRentalRecords =
            new ArrayList<>(Arrays.asList(defaultRentalRecordRequest()));

    public static RentalRecordsMessage defaultRentalRecordsMessage() {
        return defaultRentalRecordsMessageBuilder().build();
    }

    public static RentalRecordsMessage.RentalRecordsMessageBuilder defaultRentalRecordsMessageBuilder() {
        return RentalRecordsMessage.builder()
                .bookId(defaultBookId)
                .rentalRecords(defaultRentalRecords);
    }
}
