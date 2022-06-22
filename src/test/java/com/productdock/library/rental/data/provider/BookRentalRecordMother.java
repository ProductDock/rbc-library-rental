package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.ArrayList;
import java.util.Arrays;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;

public class BookRentalRecordMother {

    private static final String defaultBookId = "1";

    public static BookRentalRecord bookRentalRecordWithNoRequests() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(new ArrayList<>())
                .build();
    }

    public static BookRentalRecord bookRentalRecordWithRentRequest() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(new ArrayList<>(Arrays.asList(bookCopyWithRentRequest())))
                .build();
    }

    public static BookRentalRecord bookRentalRecordWithReserveRequest() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(new ArrayList<>(Arrays.asList(bookCopyWithReserveRequest())))
                .build();
    }

    public static BookRentalRecord.BookRentalRecordBuilder bookRentalRecordBuilder() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId);
    }
}
