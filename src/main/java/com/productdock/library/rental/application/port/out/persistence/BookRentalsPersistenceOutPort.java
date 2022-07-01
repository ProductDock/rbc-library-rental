package com.productdock.library.rental.application.port.out.persistence;

import com.productdock.library.rental.domain.BookRentals;

import java.util.Collection;
import java.util.Optional;

public interface BookRentalsPersistenceOutPort {

    Optional<BookRentals> findByBookId(String bookId);

    Collection<BookRentals> findWithReservations();

    void save(BookRentals bookRentals);
}
