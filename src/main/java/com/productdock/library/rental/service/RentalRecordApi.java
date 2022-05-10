package com.productdock.library.rental.service;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rental/record")
public record RentalRecordApi(RentalRecordService rentalRecordService) {

    @PostMapping
    @SneakyThrows
    public void createRecord(@RequestBody RentalRequestDto rentalRequestDto, Authentication authentication) {
        rentalRecordService.create(rentalRequestDto, ((Jwt) authentication.getCredentials()).getClaim("email"));
    }

    @GetMapping("/{bookId}")
    public RentalRecordsDto getBook(@PathVariable("bookId") String bookId){
        return rentalRecordService.getByBookId(bookId);
    }
}
