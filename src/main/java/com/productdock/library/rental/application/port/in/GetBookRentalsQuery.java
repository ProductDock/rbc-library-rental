package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.BookRentals;

import java.util.Collection;

public interface GetBookRentalsQuery {

    Collection<BookRentals.BookCopyRentalState> getBookCopiesRentalState(String bookId);
}
