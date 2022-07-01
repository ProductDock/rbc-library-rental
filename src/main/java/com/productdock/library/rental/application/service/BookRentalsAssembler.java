package com.productdock.library.rental.application.service;

import com.productdock.library.rental.application.port.out.persistence.BookRentalsPersistenceOutPort;
import com.productdock.library.rental.domain.BookRentals;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class BookRentalsAssembler {

    private BookRentalsPersistenceOutPort bookRentalsRepository;

    public BookRentals of(String bookId) {
        log.debug("Find current book rentals if there are any or start new BookRentals for book {}", bookId);
        return bookRentalsRepository.findByBookId(bookId).orElse(new BookRentals(bookId));
    }
}
