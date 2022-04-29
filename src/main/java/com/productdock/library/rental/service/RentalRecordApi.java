package com.productdock.library.rental.service;

import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/rental/record")
public record RentalRecordApi(RentalRecordService rentalRecordService) {

//    @PostMapping
//    @SneakyThrows
//    public void createRecord(@RequestBody RentalRequest rentalRequest, @RequestHeader("Authorization") String authToken) {
//        rentalRecordService.create(rentalRequest, authToken);
//    }

    @PostMapping
    @SneakyThrows
    public void createRecord(@RequestBody RentalRequest rentalRequest, Authentication authentication) {
        rentalRecordService.create(rentalRequest, ((Jwt) authentication.getCredentials()).getClaim("email"));
    }

//    @PostMapping
//    @SneakyThrows
//    public void createRecord(@RequestBody RentalRequest rentalRequest, Principal principal) {
//        rentalRecordService.create(rentalRequest, ((Jwt) principal).getClaim("email"));
//    }
}
