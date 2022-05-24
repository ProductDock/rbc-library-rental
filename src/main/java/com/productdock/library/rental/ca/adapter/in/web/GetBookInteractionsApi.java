package com.productdock.library.rental.ca.adapter.in.web;

import com.productdock.library.rental.ca.application.port.in.GetBookInteractionsQuery;
import com.productdock.library.rental.service.BookRecordDto;
import com.productdock.library.rental.service.BookRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/rental/record")
class GetBookInteractionsApi {

    private final GetBookInteractionsQuery getBookInteractionsQuery;

    private final BookRecordMapper bookRecordMapper;

    @GetMapping("/{bookId}")
    public Collection<BookRecordDto> getByBookId(@PathVariable("bookId") String bookId) {
        var bookInteractions = getBookInteractionsQuery.getByBookId(bookId);
        return bookRecordMapper.toDtoCollection(bookInteractions);
    }
}
