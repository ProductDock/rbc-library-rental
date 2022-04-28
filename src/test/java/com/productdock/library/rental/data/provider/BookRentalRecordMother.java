package com.productdock.library.rental.data.provider;

import com.productdock.library.rental.domain.BookRentalRecord;
import com.productdock.library.rental.service.RentalStatus;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class BookRentalRecordMother {

    private static final String defaultBookId = "1";
    private static final String defaultUserEmail = "default@gmail.com";
    private static final Date defaultDate = new Date();
    private static final RentalStatus rentStatus = RentalStatus.RENTED;
    private static final RentalStatus reserveStatus = RentalStatus.RESERVED;


    private static final List<BookRentalRecord.BookCopy> bookCopiesWithRentStatus = new LinkedList<>
            (Arrays.asList(new BookRentalRecord.BookCopy(defaultDate, defaultUserEmail, rentStatus)));

    private static final List<BookRentalRecord.BookCopy> bookCopiesWithReserveStatus = new LinkedList<>
            (Arrays.asList(new BookRentalRecord.BookCopy(defaultDate, defaultUserEmail, reserveStatus)));

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
