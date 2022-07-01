package com.productdock.library.rental.data.provider.domain;

import com.productdock.library.rental.domain.BookRentals;

import java.util.ArrayList;
import java.util.Arrays;

import static com.productdock.library.rental.data.provider.domain.BookCopyRentalStateMother.rentedBookCopy;

public class BookRentalsMother {

    private static final String defaultBookId = "1";

    public static BookRentals bookRentalsWithNoRequests() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>())
                .build();
    }

    public static BookRentals bookRentalsWithRentRequest() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>(Arrays.asList(rentedBookCopy())))
                .build();
    }

    public static BookRentals bookRentalsWithReserveRequest() {
        return BookRentals.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>(Arrays.asList(BookCopyRentalStateMother.reservedBookCopy())))
                .build();
    }

    public static BookRentals.BookRentalsBuilder bookRentalRecordBuilder() {
        return BookRentals.builder()
                .bookId(defaultBookId);
    }
}
