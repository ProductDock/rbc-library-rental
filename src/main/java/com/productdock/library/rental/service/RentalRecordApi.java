package com.productdock.library.rental.service;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rental/record")
public record RentalRecordApi(RentalRecordService rentalRecordService) {

    @PostMapping
    public void createRecord(@RequestBody RentalRecordDto rentalRecordDTO, @RequestHeader("Authorization") String authToken) {
        rentalRecordService.create(rentalRecordDTO, authToken);
    }

//    @PostMapping
//    public void createRecord(@RequestBody RentalRecordDto rentalRecordDTO, Principal principal) {
//        UserDetails currentUser = (UserDetails) principal;
//        System.out.println("Retrieved email is : " + currentUser);
//        //rentalRecordService.create(rentalRecordDTO, authToken);
//    }
}
