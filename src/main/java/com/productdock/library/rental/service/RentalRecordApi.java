package com.productdock.library.rental.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping("api/rental/record")
public record RentalRecordApi(RentalRecordService rentalRecordService) {

    @PostMapping
    @SneakyThrows
    public void createRecord(@RequestBody RentalRequestDto rentalRequestDto, Authentication authentication) {
        log.debug("POST request received - api/rental/record, Payload: {}", rentalRequestDto);
        rentalRecordService.create(rentalRequestDto, ((Jwt) authentication.getCredentials()).getClaim("email"));
    }

    @GetMapping("/{bookId}")
    public Collection<BookRecordDto> getByBookId(@PathVariable("bookId") String bookId) {
        log.debug("GET request received - api/rental/record/{}", bookId);
        return rentalRecordService.getByBookId(bookId);
    }
}
