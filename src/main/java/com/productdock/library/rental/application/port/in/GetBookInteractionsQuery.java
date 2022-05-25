package com.productdock.library.rental.application.port.in;

import com.productdock.library.rental.domain.BookInteraction;

import java.util.Collection;

public interface GetBookInteractionsQuery {

    Collection<BookInteraction> getByBookId(String bookId);
}
