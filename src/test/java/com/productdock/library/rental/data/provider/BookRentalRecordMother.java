package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithReserveRequest;

public class BookRentalRecordMother {

    private static final String defaultBookId = "1";

    public static BookRentalRecord bookRentalRecordWithRentRequest() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(new ArrayList<>(Arrays.asList(defaultBookCopyWithRentRequest())))
                .build();
    }

    public static BookRentalRecord bookRentalRecordWithReserveRequest() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(new ArrayList<>(Arrays.asList(defaultBookCopyWithReserveRequest())))
                .build();
    }

}
