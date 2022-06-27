package com.productdock.library.rental.data.provider.out.mongo;

import com.productdock.library.rental.adapter.out.mongo.entity.BookCopyRentalState;
import com.productdock.library.rental.adapter.out.mongo.entity.BookRentalState;
import com.productdock.library.rental.domain.RentalStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.out.mongo.BookCopyRentalStateMother.bookCopyRentalStateWithRequest;
import static com.productdock.library.rental.data.provider.out.mongo.BookCopyRentalStateMother.defaultBookCopyRentalState;

public class BookRentalStateMother {

    private static final String defaultId = "2";
    private static final String defaultBookId = "1";

    private static final List<BookCopyRentalState> defaultBookCopyRentalState = new ArrayList<>
            (Arrays.asList(defaultBookCopyRentalState()));

    public static BookRentalState defaultBookRentalState() {
        return defaultBookRentalStateBuilder().build();
    }

    public static BookRentalState.BookRentalStateBuilder defaultBookRentalStateBuilder() {
        return BookRentalState.builder()
                .id(defaultId)
                .bookId(defaultBookId)
                .bookCopiesRentalState(defaultBookCopyRentalState);
    }

    public static BookRentalState bookRentalStateWithNoRequests() {
        return BookRentalState.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>())
                .build();
    }

    public static BookRentalState bookRentalStateWithRentalRequest(RentalStatus rentalStatus) {
        return BookRentalState.builder()
                .bookId(defaultBookId)
                .bookCopiesRentalState(new ArrayList<>(Arrays.asList(bookCopyRentalStateWithRequest(rentalStatus))))
                .build();
    }


}
