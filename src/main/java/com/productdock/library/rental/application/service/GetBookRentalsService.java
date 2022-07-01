package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.in.GetBookRentalsQuery;
import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Slf4j
@Service
@AllArgsConstructor
class GetBookRentalsService implements GetBookRentalsQuery {

    private BookRentalsPersistenceOutPort rentalRecordRepository;

    @Override
    public Collection<BookRentals.BookCopyRentalState> getBookCopiesRentalState(String bookId) {
        log.debug("Get book copies from rental record for the {} book", bookId);
        return rentalRecordRepository
                .findByBookId(bookId)
                .map(BookRentals::getBookCopiesRentalState)
                .orElse(new ArrayList<>());
    }
}
