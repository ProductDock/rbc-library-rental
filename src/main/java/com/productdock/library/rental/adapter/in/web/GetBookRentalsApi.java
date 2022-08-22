package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.application.port.in.GetBookRentalsQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/rental/book/")
public record GetBookRentalsApi(GetBookRentalsQuery getBookRentalsQuery, BookCopyRentalStateMapper bookCopyRentalStateMapper) {

    @GetMapping("/{bookId}/rentals")
    public Collection<BookCopyRentalStateDto> getByBookId(@PathVariable("bookId") String bookId) throws URISyntaxException, IOException, InterruptedException {
        log.debug("GET request received - api/rental/record/{}", bookId);
        var bookCopiesRentalState = getBookRentalsQuery.getBookCopiesRentalState(bookId);
        return bookCopyRentalStateMapper.toDtoCollection(bookCopiesRentalState);
    }
}
