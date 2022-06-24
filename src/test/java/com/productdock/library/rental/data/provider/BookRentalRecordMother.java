package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentals;

import java.util.ArrayList;
import java.util.Arrays;

import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.bookCopyWithReserveRequest;

public class BookRentalRecordMother {

    private static final String defaultBookId = "1";

    public static BookRentals bookRentalRecordWithNoRequests() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>())
                .build();
    }

    public static BookRentals bookRentalRecordWithRentRequest() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>(Arrays.asList(bookCopyWithRentRequest())))
                .build();
    }

    public static BookRentals bookRentalRecordWithReserveRequest() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>(Arrays.asList(bookCopyWithReserveRequest())))
                .build();
    }

    public static BookRentals.BookRentalsBuilder bookRentalRecordBuilder() {
        return BookRentals.builder()
                .bookId(defaultBookId);
    }
}
