package com.productdock.library.rental.service;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rental/record")
public record RentalRecordApi(RentalRecordService rentalRecordService) {

    @PostMapping
    @SneakyThrows
    public void createRecord(@RequestBody RentalRequest rentalRequest, @RequestHeader("Authorization") String authToken) {
        rentalRecordService.create(rentalRequest, authToken);
    }

//    @PostMapping
//    public void createRecord(@RequestBody RentalRecordDto rentalRecordDTO, Principal principal) {
//        UserDetails currentUser = (UserDetails) principal;
//        System.out.println("Retrieved email is : " + currentUser);
//        //rentalRecordService.create(rentalRecordDTO, authToken);
//    }
}
