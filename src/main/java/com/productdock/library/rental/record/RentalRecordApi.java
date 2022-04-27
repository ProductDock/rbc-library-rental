package com.productdock.library.rental.record;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
