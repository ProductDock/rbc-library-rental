package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.BookRentalRecord;

import java.util.Collection;

public interface GetRentalRecordQuery {

    Collection<BookRentalRecord.BookCopy> getBookCopiesByBookId(String bookId);
}
