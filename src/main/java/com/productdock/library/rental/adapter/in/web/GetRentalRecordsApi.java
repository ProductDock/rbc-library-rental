package com.productdock.library.rental.adapter.in.web;

import com.productdock.library.rental.application.port.in.GetRentalRecordQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/rental/record")
public record GetRentalRecordsApi(GetRentalRecordQuery getRentalRecordQuery, BookRecordMapper bookRecordMapper) {

    @GetMapping("/{bookId}")
    public Collection<BookRecordDto> getByBookId(@PathVariable("bookId") String bookId) {
        log.debug("GET request received - api/rental/record/{}", bookId);
        var interactions = getRentalRecordQuery.getBookCopiesByBookId(bookId);
        return bookRecordMapper.toDtoCollection(interactions);
    }
}
