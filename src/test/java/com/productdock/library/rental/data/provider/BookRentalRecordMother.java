package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithRentRequest;
import static com.productdock.library.rental.data.provider.BookCopyMother.defaultBookCopyWithReserveRequest;

public class BookRentalRecordMother {

    private static final String defaultBookId = "1";

    private static final List<BookRentalRecord.BookCopy> bookCopiesWithRentStatus = new ArrayList<>
            (Arrays.asList(defaultBookCopyWithRentRequest()));

    private static final List<BookRentalRecord.BookCopy> bookCopiesWithReserveStatus = new ArrayList<>
            (Arrays.asList(defaultBookCopyWithReserveRequest()));

    public static BookRentalRecord defaultBookRentalRecordWithRentRequest() {
        return defaultBookRentalRecordBuilder().bookCopies(bookCopiesWithRentStatus).build();
    }

    public static BookRentalRecord defaultBookRentalRecordWithReserveRequest() {
        return defaultBookRentalRecordBuilder().bookCopies(bookCopiesWithReserveStatus).build();
    }

    public static BookRentalRecord.BookRentalRecordBuilder defaultBookRentalRecordBuilder() {
        return BookRentalRecord.builder()
                .bookId(defaultBookId)
                .bookCopies(bookCopiesWithRentStatus);
    }
}
