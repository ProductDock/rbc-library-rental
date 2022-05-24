package com.productdock.library.rental.ca.application.port.in;

import com.productdock.library.rental.ca.domain.BookInteraction;

import java.util.Collection;

public interface GetBookInteractionsQuery {

    Collection<BookInteraction> getByBookId(String bookId);
}
