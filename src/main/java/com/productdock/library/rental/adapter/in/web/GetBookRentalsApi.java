package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.application.port.in.GetBookRentalsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/rental/record")
public record GetBookRentalsApi(GetBookRentalsQuery getBookRentalsQuery, BookCopyRentalStateMapper bookCopyRentalStateMapper) {

    @GetMapping("/{bookId}")
    public Collection<BookCopyRentalStateDto> getByBookId(@PathVariable("bookId") String bookId) {
        log.debug("GET request received - api/rental/record/{}", bookId);
        var bookCopiesRentalState = getBookRentalsQuery.getBookCopiesRentalState(bookId);
        return bookCopyRentalStateMapper.toDtoCollection(bookCopiesRentalState);
    }
}
